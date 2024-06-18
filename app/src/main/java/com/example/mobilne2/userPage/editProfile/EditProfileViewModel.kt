package com.example.mobilne2.userPage.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.userPage.mapper.asUserUI
import com.example.mobilne2.userPage.repository.UserRepostiory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: UserRepostiory,
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()
    private fun setState(reducer: EditProfileState.() -> EditProfileState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<EditProfileState.Events>()
    fun setEvent(event: EditProfileState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        loadEditProfile()
    }

    private fun loadEditProfile() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val user = repository.getUserByID(1).asUserUI()
                setState { copy(user = user) }
                setState { copy(firstName = user.firstName) }
                setState { copy(lastName = user.lastName) }
                setState { copy(nickname = user.nickname) }
                setState { copy(email = user.email) }

            } catch (error: Exception) {
                setState { copy(error = EditProfileState.Error.LoadingFailed()) }
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is EditProfileState.Events.Reset -> {
                        setState { copy(reset = it.reset) }
                    }
                    is EditProfileState.Events.EditFirstName -> {
                        val firstName = it.firstName
                        val firstNameRegex = "^[A-Z][a-z]{2,}$".toRegex()
                        if (firstName.isEmpty() || !firstNameRegex.matches(firstName)) {
                            setEvent(EditProfileState.Events.Reset(true))
                            setState { copy(error = EditProfileState.Error.BadFirstName()) }
                        } else {
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateFirstName(userID, firstName)
                                setState { copy(firstName = firstName) }
                            }
                        }
                    }
                    is EditProfileState.Events.EditLastName -> {
                        val lastName = it.lastName
                        val lastNameRegex = "^[A-Z][a-z]{2,}$".toRegex()
                        if (lastName.isEmpty() || !lastNameRegex.matches(lastName)) {
                            setEvent(EditProfileState.Events.Reset(true))
                            setState { copy(error = EditProfileState.Error.BadLastName()) }
                        } else {
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateLastName(userID, lastName)
                                setState { copy(lastName = lastName) }
                            }
                        }
                    }
                    is EditProfileState.Events.EditNickname -> {
                        val nickname = it.nickname
                        val nicknameRegex = "^[A-Za-z0-9_]{3,}$".toRegex()
                        if (nickname.isEmpty() || !nicknameRegex.matches(nickname)) {
                            setEvent(EditProfileState.Events.Reset(true))
                            setState { copy(error = EditProfileState.Error.BadNickname()) }
                        } else {
                            val userID = state.value.user?.id
                            if (userID != null) {
                                try {
                                    repository.updateNickname(userID, nickname)
                                    setState { copy(nickname = nickname) }
                                } catch (e: Exception) {
                                    setEvent(EditProfileState.Events.Reset(true))
                                    setState { copy(error = EditProfileState.Error.PersonExist()) }
                                }
                            }
                        }
                    }
                    is EditProfileState.Events.EditEmail -> {
                        val email = it.email
                        val emailRegex =
                            "^[A-Za-z0-9_]{3,}@[A-Za-z0-9_]{3,}\\.[A-Za-z0-9_]{2,}$".toRegex()
                        if (email.isEmpty() || !emailRegex.matches(email)) {
                            setEvent(EditProfileState.Events.Reset(true))
                            setState { copy(error = EditProfileState.Error.BadEmail()) }
                        } else {
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateEmail(userID, email)
                                setState {
                                    copy(email = email)
                                }
                            }
                        }

                    }
                }
            }
        }

    }
}