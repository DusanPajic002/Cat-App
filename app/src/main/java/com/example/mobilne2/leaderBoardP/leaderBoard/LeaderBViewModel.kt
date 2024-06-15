package com.example.mobilne2.leaderBoardP.leaderBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.leaderBoardP.leaderBoard.model.LeaderBoardUI
import com.example.mobilne2.leaderBoardP.mapper.asLeaderBoardUI
import com.example.mobilne2.leaderBoardP.repository.LeaderBoardRepository
import com.example.mobilne2.quizScreen.quiz.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class LeaderBViewModel @Inject constructor(
    private val repository: LeaderBoardRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LeaderBState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LeaderBState.() -> LeaderBState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<LeaderBState.Events>()
    fun setEvent(event: LeaderBState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        observerEvents()
        loadLeaderBoard()
    }

    private fun observerEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is LeaderBState.Events.changePage -> {
                        setState { copy(page = it.changePage) }
                        val start = (state.value.page - 1) * state.value.dataPerPage
                        val end = min(start + state.value.dataPerPage, state.value.leaderBoardOnline.size)

                        val leaderBoardOnlinePerPage = state.value.leaderBoardOnline.subList(start, end)
                        setState { copy(leaderBoardOnlinePerPage = leaderBoardOnlinePerPage)}
                    }
                }
            }
        }
    }

    private fun loadLeaderBoard() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {

                val leaderBoardOnline = repository.getLeaderBoardOnline(1).map { it.asLeaderBoardUI() }
                setState { copy(leaderBoardOnline = leaderBoardOnline ) }

                val nPage1 = leaderBoardOnline.size/state.value.dataPerPage
                val nPage2 = if ((leaderBoardOnline.size % state.value.dataPerPage) != 0) 1 else 0
                setState { copy(maxPage = ( nPage1 + nPage2 )) }

                val leaderBoardOnlinePerPage = state.value.leaderBoardOnline.subList(0, state.value.dataPerPage)
                setState { copy(leaderBoardOnlinePerPage = leaderBoardOnlinePerPage)}

            } catch (error: Exception) {
                //setState { copy(error =
            } finally {
                setState {
                    copy(fetching = false)
                }
            }
        }
    }


}