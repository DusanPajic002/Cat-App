package com.example.mobilne2.catListP.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catListP.api.model.CatApiModel
import com.example.mobilne2.catListP.list.model.CatListUI
import com.example.mobilne2.catListP.list.CatListState.FilterEvent
import com.example.mobilne2.catListP.mappers.asCatDbModel
import com.example.mobilne2.catListP.mappers.asCatModel
import com.example.mobilne2.catListP.repository.CatListRepostiory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val repository: CatListRepostiory,
) : ViewModel() {

    private val _state = MutableStateFlow(CatListState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatListState.() -> CatListState) = _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<FilterEvent>()
    fun setEvent(event: FilterEvent) = viewModelScope.launch { events.emit(event) }
    init {
        observeEvents()
        fetchCats()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is FilterEvent.filterClick -> {
                        filterEvent(filter = state.value.filter)
                    }
                    is FilterEvent.filterEvent -> {
                        setState {
                            copy(filter = it.textfilt)
                        }
                    }
                }
            }
        }
    }

    private fun filterEvent(filter: String){
        viewModelScope.launch {
            try {
                if(filter.isEmpty() || filter.isBlank())
                    setState { copy(filteredCats = state.value.cats) }
                else
                    setState { copy(filteredCats = state.value.cats.filter { it.name.startsWith(filter, ignoreCase = true) }) }
            } catch (error: Exception) {
            }
        }
    }

    private fun fetchCats() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val cats = withContext(Dispatchers.IO) {repository.getAllCats()}

                setState { copy(cats = cats.map { it.asCatModel() })}
                setState { copy(filteredCats = state.value.cats ) }

            } catch (error: Exception) {
                setState { copy(error = CatListState.ListError.ListUpdateFailed(cause = error)) }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }


}