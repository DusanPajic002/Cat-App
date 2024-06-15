package com.example.mobilne2.userPage.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class RegisterViewModel @Inject constructor(
    private val repository: UserRepostiory,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()
    private fun setState(reducer: RegisterState.() -> RegisterState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<RegisterState.Events>()
    fun setEvent(event: RegisterState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        existing()
    }

    private fun existing() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {
                val exist = repository.getUserCount() > 3;
                setState { copy(exist = exist) }
            } catch (error: Exception) {
                println("Errorr: ${error.message}")
                setState { copy(error = RegisterState.Error.LoadingFailed()) }
            } finally {
                setState {  copy(fatching = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is RegisterState.Events.Register -> {
                        val fullName = it.fullName
                        val nickname = it.nickname
                        val email = it.email
                        val fullNameRegex = "^[A-Z][a-z]{3,} [A-Z][a-z]{3,}$".toRegex()
                        val nicknameRegex = "^[A-Za-z0-9_]{3,}$".toRegex()
                        val emailRegex =
                            "^[A-Za-z0-9_]{3,}@[A-Za-z0-9_]{3,}\\.[A-Za-z0-9_]{2,}$".toRegex()

                        if (fullName.isEmpty() || email.isEmpty() || nickname.isEmpty()) {
                            setState { copy(error = RegisterState.Error.MissingParts()) }
                        } else if (fullName.isNotEmpty() && !fullName.matches(fullNameRegex)) {
                            setState { copy(error = RegisterState.Error.BadFullName()) }
                        } else if (nickname.isNotEmpty() && !nickname.matches(nicknameRegex)) {
                            setState { copy(error = RegisterState.Error.BadNickname()) }
                        } else if (email.isNotEmpty() && !email.matches(emailRegex)) {
                            setState { copy(error = RegisterState.Error.BadEmail()) }
                        } else {
                            val user = User(
                                id = 0,
                                firstName = fullName.split(" ")[0],
                                lastName = fullName.split(" ")[1],
                                nickname = nickname,
                                email = email
                            )
                            try {
                                repository.insertUser(user)
                                setState {copy(SuccesRegister = true)}
                            } catch (error: Exception) {
                                setState { copy(error = RegisterState.Error.PersonExist()) }
                            }
                        }
                    }
                }
            }
        }
    }

}