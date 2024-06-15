package com.example.mobilne2.quizScreen.quiz

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardReq
import com.example.mobilne2.leaderBoardP.db.LeaderBoard
import com.example.mobilne2.quizScreen.model.CatQuestion
import com.example.mobilne2.quizScreen.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()
    private fun setState(reducer: QuizState.() -> QuizState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<QuizState.Events>()
    fun setEvent(event: QuizState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        setState { copy(fullTime = 300) }
        setState { copy(category = 1) }
        observeEvents()
        loadQuiz()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is QuizState.Events.changeQuestion -> {
                        setState { copy(questionNumber = it.number) }
                    }
                    is QuizState.Events.updateCorrectAnswers -> {
                        if (it.answer.equals(state.value.questions[state.value.questionNumber].correctAnswer)) {
                            setState { copy(score = state.value.score + 1) }
                            setState { copy(correctAnswers = state.value.correctAnswers + 1) }
                        }
                    }
                    is QuizState.Events.updateScore -> {
                        val score = state.value.correctAnswers * 2.5 * (1 + (state.value.remainingTime + 120) / state.value.fullTime)
                        setState { copy(score = score) }
                    }
                    is QuizState.Events.updateFinish -> {
                        setState { copy(finished = it.finished) }
                    }
                    is QuizState.Events.updateCancle -> {
                        setState { copy(cancled = it.cancled) }
                    }
                    is QuizState.Events.publishEvent -> {
                        setState { copy(finished = true)}
                        if(it.publish){
                            val leaderBoardData = LeaderBoardReq (
                                nickname = state.value.userNickname,
                                result = state.value.score,
                                category = state.value.category,
                            )
                            repository.pusblishOnline(leaderBoardData)
                        }else{
                            val time = LocalTime.now().toSecondOfDay()
                            val leaderBoardData = LeaderBoard(
                                userID = state.value.userID,
                                nickname = state.value.userNickname,
                                result = state.value.score,
                                createdAt = time.toLong(),
                            )
                            repository.pusblishPrivate(leaderBoardData)
                        }
                    }
                }
            }
        }
    }

    private fun loadQuiz() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val allCats = withContext(Dispatchers.IO) {repository.getAllCats()}
                val temp = allCats.flatMap { it.temperament.split(", ") }.distinct()
                val breed = allCats.map { it.origin }.distinct()
                val cats20 = allCats.filter { !it.id.equals("mala") }.shuffled().take(20)
                val user = repository.getUserByID(1)
                setState { copy(userNickname = user.nickname)}
                setState { copy(userID = user.id)}

                cats20.forEach { cat ->
                    withContext(Dispatchers.IO) {
                        repository.featchAllImagesCatID(cat.id)
                    }
                }

                setState { copy(temp = temp) }
                setState { copy(breed = breed) }

                val questions = ArrayList<CatQuestion>()

                for(cat in cats20){
                    val i = Random.nextInt(1, 4)
                    when(i){
                        1 -> questions.add(makeQuestions1(cat))
                        2 -> questions.add(makeQuestions2(cat))
                        3 -> questions.add(makeQuestions3(cat))
                    }
                }

                setState { copy(questions = questions)}

            } catch (error: Exception) {
                setState { copy(error = QuizState.Error.ErrorToLoadQuiz) }
            } finally {
                timer.start()
                setState { copy(loading = false) }
            }
        }
    }

    private suspend fun makeQuestions1(cat: Cat): CatQuestion{
        val breed = cat.origin
        val origin = state.value.breed.filter { it != breed }.shuffled().take(3)
        val answers = (origin + breed).shuffled()
        val catImages = repository.getAllImagesCatID(cat.id).shuffled().first()

        return CatQuestion(
            question = "What breed is the cat in the picture??",
            catImage = catImages.url,
            correctAnswer = breed,
            answers = answers
        )
    }

    private suspend fun makeQuestions2(cat: Cat): CatQuestion{
        val catTemper = cat.temperament.split(", ")
        val incorrectTemper = state.value.temp.filter { it !in catTemper }.shuffled().first()
        val answers = (catTemper.shuffled().take(3) + incorrectTemper).shuffled()
        val catImages = repository.getAllImagesCatID(cat.id).shuffled().first()

        return CatQuestion(
            question = "Kick the intruder out?",
            catImage = catImages.url,
            correctAnswer = incorrectTemper,
            answers = answers
        )
    }

    private suspend fun makeQuestions3(cat: Cat): CatQuestion{
        val catTemper = cat.temperament.split(", ")
        val incorrectTemper = state.value.temp.filter { it !in catTemper }.shuffled().take(3)
        val answers = (incorrectTemper + catTemper.shuffled().first()).shuffled()
        val catImages = repository.getAllImagesCatID(cat.id).shuffled().first()

        return CatQuestion(
            question = "Throw out the temper that does not belong?",
            catImage = catImages.url,
            correctAnswer = cat.life_span,
            answers = answers
        )
    }

    private val timer = object: CountDownTimer((state.value.fullTime * 1000).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            setState { copy(remainingTime = millisUntilFinished / 1000) }
        }
        override fun onFinish() {
            setEvent(QuizState.Events.updateFinish(true))
        }
    }


}

