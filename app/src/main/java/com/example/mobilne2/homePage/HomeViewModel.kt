package com.example.mobilne2.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catListP.repository.CatListRepostiory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryList: CatListRepostiory,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private fun setState(reducer: HomeState.() -> HomeState) =
        _state.getAndUpdate(reducer)

    init {
        fetchCats()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                withContext(Dispatchers.IO){repositoryList.fetchAllCats()}

            } catch (error: Exception) {
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }


}