package com.example.mobilne2.userPage.myProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

                val publicLB = repositoryLB.getLeaderBoardOnline(1).map { it.asLeaderBoardUI() }
                setState { copy(publicLB = publicLB ) }

                val privateLB = repositoryLB.getPLBbyNickName(user.nickname).map { it.asLeaderBoardUI() }
                setState { copy(privateLB = privateLB ) }

                val leaderBoard = publicLB + privateLB
                setState { copy(leaderBoard = leaderBoard) }

                setState { copy(maxPage = leaderBoard.size/state.value.dataPerPage + 1 ) }

                val usersPerPage = state.value.leaderBoard.subList(0, state.value.dataPerPage)
                setState { copy(usersPerPage = usersPerPage)}

            } catch (error: Exception) {
                setState { copy(error = MyProfileState.Error.LoadingFailed) }
            } finally {
                setState {  copy(loading = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {

                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }

}