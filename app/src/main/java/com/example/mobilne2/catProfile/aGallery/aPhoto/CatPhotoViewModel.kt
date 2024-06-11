package com.example.mobilne2.catProfile.aGallery.aPhoto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catProfile.aGallery.repository.CatGalleryRepository
import com.example.mobilne2.catProfile.mapper.asCatImageUiModel
import com.example.mobilne2.navigation.catPhotoId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatPhotoViewModel  @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CatGalleryRepository,
) : ViewModel() {

    private val imageID: String = savedStateHandle.catPhotoId
    private val _state = MutableStateFlow(CatPhotoState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatPhotoState.() -> CatPhotoState) =
        _state.getAndUpdate(reducer)


    init {
        fetchCatGallery()
    }

    private fun fetchCatGallery() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {

                var catID = withContext(Dispatchers.IO) { repository.getCatID(imageID) }
                setState { copy(catID = catID)}

                val images = withContext(Dispatchers.IO) { repository.getAllImagesCatID(catID) }
                val imageIndex = images.indexOfFirst { it.id == imageID }

                setState { copy(photos = images.map { it.asCatImageUiModel() }) }
                setState { copy(imageIndex = imageIndex.toString())}


            } catch (error: Exception) {
                setState { copy(catID = "")}
                setState { copy(imageIndex = "")}
                setState { copy(error = CatPhotoState.DetailsError.DataUpdateFailed(cause = error)) }
            } finally {
                setState {
                    copy(fetching = false)
                }
            }
        }
    }

}
