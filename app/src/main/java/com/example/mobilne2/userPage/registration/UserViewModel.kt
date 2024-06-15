package com.example.mobilne2.userPage.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.leaderBoardP.mapper.asLeaderBoardUI
import com.example.mobilne2.userPage.db.User
import com.example.mobilne2.userPage.repository.UserRepostiory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepostiory,
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UserState.() -> UserState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<UserState.Events>()
    fun setEvent(event: UserState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        existing()
    }

    private fun existing() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {
                val exist = repository.getUserCount() > 0;
                setState { copy(exist = exist) }
            } catch (error: Exception) {
                setState { copy(error = UserState.Error.PersonExist) }
            } finally {
                setState {  copy(fatching = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is UserState.Events.Register -> {
                        val fullName = it.fullName
                        val nickname = it.nickname
                        val email = it.email
                        val fullNameRegex = "^[A-Z][a-z]{3,} [A-Z][a-z]{3,}$".toRegex()
                        val nicknameRegex = "^[A-Za-z0-9_]{3,}$".toRegex()
                        val emailRegex =
                            "^[A-Za-z0-9_]{3,}@[A-Za-z0-9_]{3,}\\.[A-Za-z0-9_]{2,}$".toRegex()

                        if (fullName.isEmpty() || email.isEmpty() || nickname.isEmpty()) {
                            setState { copy(error = UserState.Error.MissingParts) }
                        } else if (fullName.isNotEmpty() && !fullName.matches(fullNameRegex)) {
                            setState { copy(error = UserState.Error.BadFullName) }
                        } else if (nickname.isNotEmpty() && !nickname.matches(nicknameRegex)) {
                            setState { copy(error = UserState.Error.BadNickname) }
                        } else if (email.isNotEmpty() && !email.matches(emailRegex)) {
                            setState { copy(error = UserState.Error.BadEmail) }
                        } else {
                            val user = User(
                                id = 0,
                                firstName = fullName.split(" ")[0],
                                lastName = fullName.split(" ")[1],
                                nickname = nickname,
                                email = email
                            )
                            repository.insertUser(user)
                            setState {copy(SuccesRegister = true)}
                        }
                    }
                }
            }
        }
    }

}