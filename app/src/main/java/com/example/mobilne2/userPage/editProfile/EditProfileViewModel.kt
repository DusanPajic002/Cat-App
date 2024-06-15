package com.example.mobilne2.userPage.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        loadMyProfile()
    }

    private fun loadMyProfile() {
        viewModelScope.launch {
            setState { copy(fatching = true) }
            try {

            } catch (error: Exception) {
                setState { copy(error = EditProfileState.Error.LoadingFailed) }
            } finally {
                setState {  copy(fatching = false) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is EditProfileState.Events.EditFirstName -> {
                        val firstName = it.firstName
                        val firstNameRegex = "^[A-Z][a-z]{3,}$".toRegex()
                        if (firstName.isEmpty() || !firstNameRegex.matches(firstName)) {
                            setState { copy(error = EditProfileState.Error.BadFirstName) }
                        }else{
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateFirstName(userID, firstName)
                            }
                        }
                    }
                    is EditProfileState.Events.EditLastName -> {
                        val lastName = it.lastName
                        val lastNameRegex = "^[A-Z][a-z]{3,}$".toRegex()
                        if (lastName.isEmpty() || !lastNameRegex.matches(lastName)) {
                            setState { copy(error = EditProfileState.Error.BadLastName) }
                        }else{
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateLastName(userID, lastName)
                            }
                        }
                    }
                    is EditProfileState.Events.EditNickname -> {
                        val nickname = it.nickname
                        val nicknameRegex = "^[A-Za-z0-9_]{3,}$".toRegex()
                        if (nickname.isEmpty() || !nicknameRegex.matches(nickname)) {
                            setState { copy(error = EditProfileState.Error.BadNickname) }
                        }else{
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateNickname(userID, nickname)
                            }
                        }
                    }
                    is EditProfileState.Events.EditEmail -> {
                        val email = it.email
                        val emailRegex ="^[A-Za-z0-9_]{3,}@[A-Za-z0-9_]{3,}\\.[A-Za-z0-9_]{2,}$".toRegex()
                        if (email.isEmpty() || !emailRegex.matches(email)) {
                            setState { copy(error = EditProfileState.Error.BadEmail) }
                        }else {
                            val userID = state.value.user?.id
                            if (userID != null) {
                                repository.updateEmail(userID, email)
                            }
                        }
                    }
                }
            }
        }
    }

}