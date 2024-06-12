package com.example.mobilne2.leaderBoardP.leaderBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.leaderBoardP.repository.LeaderBoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBViewModel @Inject constructor(
    private val repository: LeaderBoardRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LeaderBState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LeaderBState.() -> LeaderBState) =
        _state.getAndUpdate(reducer)

    init {
        loadLeaderBoard()
    }

    private fun loadLeaderBoard() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {


//                val leaderBoard = repository.getLeaderBoard()
//                setState { copy(leaderBoard = leaderBoard) }

            } catch (error: Exception) {
                setState { copy(error = LeaderBState.LeaderBError.DataUpdateFailed(cause = error)) }
            } finally {
                setState {
                    copy(fetching = false)
                }
            }
        }
    }


}