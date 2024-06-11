package com.example.mobilne2.catProfile.aGallery.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catProfile.aGallery.repository.CatGalleryRepository
import com.example.mobilne2.catProfile.mapper.asCatImageUiModel
import com.example.mobilne2.catProfile.mapper.asCatUiModel
import com.example.mobilne2.navigation.catProfileId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


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


    init {
        fetchCatGallery()
    }

    private fun fetchCatGallery() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                withContext(Dispatchers.IO) { repository.fetchImages(catId) }

                val image = withContext(Dispatchers.IO) { repository.getAllImagesCatID(catId) }
                setState { copy(album = image.map { it.asCatImageUiModel() }) }

            } catch (error: Exception) {
                setState { copy(catId = "") }
                setState { copy(error = CatGalleryState.DetailsError.DataUpdateFailed(cause = error)) }
            } finally {
                setState {
                    copy(fetching = false)
                }
            }
        }
    }




}