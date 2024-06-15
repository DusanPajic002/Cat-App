package com.example.mobilne2.catProfile.aGallery.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catProfile.aGallery.repository.CatGalleryRepository
import com.example.mobilne2.catProfile.mapper.asCatImageUiModel
import com.example.mobilne2.leaderBoardP.leaderBoard.LeaderBState
import com.example.mobilne2.navigation.catProfileId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.min


@HiltViewModel
class CatGalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CatGalleryRepository,
) : ViewModel() {

    private val catId: String = savedStateHandle.catProfileId
    private val _state = MutableStateFlow(CatGalleryState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatGalleryState.() -> CatGalleryState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<CatGalleryState.Events>()
    fun setEvent(event: CatGalleryState.Events) = viewModelScope.launch { events.emit(event) }

    init {
        observerEvents()
        fetchCatGallery()
    }

    private fun observerEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is CatGalleryState.Events.changePage -> {
                        setState { copy(page = it.changePage) }
                        val start = (state.value.page - 1) * state.value.dataPerPage
                        val end = min(start + state.value.dataPerPage, state.value.album.size)

                        val catsPerPage = state.value.album.subList(start, end)
                        setState { copy(catsPerPage = catsPerPage)}
                    }
                }
            }
        }
    }

    private fun fetchCatGallery() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {

                withContext(Dispatchers.IO) { repository.fetchImages(catId) }

                val album = withContext(Dispatchers.IO) { repository.getAllImagesCatID(catId) }
                setState { copy(album = album.map { it.asCatImageUiModel() }) }

                val nPage1 = album.size/state.value.dataPerPage
                val nPage2 = if ((album.size % state.value.dataPerPage) != 0) 1 else 0
                setState { copy(maxPage = ( nPage1 + nPage2 )) }

                val catsPerPage = state.value.album.subList(0, state.value.dataPerPage)
                setState { copy(catsPerPage = catsPerPage)}

            } catch (error: Exception) {
                setState { copy(error = CatGalleryState.Error.LoadError()) }
            } finally {
                setState {copy(fetching = false)}
            }
        }
    }



}