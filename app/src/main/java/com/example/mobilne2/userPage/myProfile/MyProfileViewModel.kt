package com.example.mobilne2.userPage.myProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.leaderBoardP.leaderBoard.LeaderBState
import com.example.mobilne2.leaderBoardP.mapper.asLeaderBoardUI
import com.example.mobilne2.leaderBoardP.repository.LeaderBoardRepository
import com.example.mobilne2.userPage.repository.UserRepostiory
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
class MyProfileViewModel @Inject constructor(
    private val repository: UserRepostiory,
    private val repositoryLB: LeaderBoardRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MyProfileState())
    val state = _state.asStateFlow()
    private fun setState(reducer: MyProfileState.() -> MyProfileState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<MyProfileState.Events>()
    fun setEvent(event: MyProfileState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        loadMyProfile()
        observeEvents()
    }

    private fun loadMyProfile() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val user = repository.getUserByID(1)
                setState { copy(user = user) }

                val privateLB = repositoryLB.getPLBbyUserID(user.id).map { it.asLeaderBoardUI() }
                setState { copy(privateLB = privateLB ) }

                val leaderBoard = (privateLB).sortedByDescending { it.createdAt }
                setState { copy(leaderBoard = leaderBoard) }

                val nPage1 = leaderBoard.size/state.value.dataPerPage
                val nPage2 = if ((leaderBoard.size % state.value.dataPerPage) != 0) 1 else 0
                val maxPages = max(nPage1 + nPage2,1)
                setState { copy(maxPage = maxPages) }

                val usersPerPage = state.value.leaderBoard.subList(0, min(state.value.dataPerPage, state.value.leaderBoard.size))
                setState { copy(usersPerPage = usersPerPage)}

            } catch (error: Exception) {
                setState { copy(error = MyProfileState.Error.LoadingFailed()) }
            } finally {
                setState {  copy(loading = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is MyProfileState.Events.changePage -> {
                        setState { copy(page = it.changePage) }
                        val start = (state.value.page - 1) * state.value.dataPerPage
                        val end = min(start + state.value.dataPerPage, state.value.leaderBoard.size)

                        val usersPerPage = state.value.leaderBoard.subList(start, end)
                        setState { copy(usersPerPage = usersPerPage)}
                    }
                }
            }
        }
    }

}