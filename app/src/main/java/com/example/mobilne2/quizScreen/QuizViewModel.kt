package com.example.mobilne2.quizScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mobilne2.catProfile.aGallery.gallery.CatGalleryState
import com.example.mobilne2.navigation.catProfileId
import com.example.mobilne2.quizScreen.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()
    private fun setState(reducer: QuizState.() -> QuizState) =
        _state.getAndUpdate(reducer)


    init {
        loadQuiz()
    }

    private fun loadQuiz() {

    }


}

