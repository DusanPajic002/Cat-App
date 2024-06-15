package com.example.mobilne2.catProfile.aGallery.aPhoto

import com.example.mobilne2.catProfile.profile.model.CatImageUI

data class CatPhotoState (
    val fetching: Boolean = false,
    val catID: String = "",
    val imageIndex: String = "",
    val photos: List<CatImageUI> = emptyList(),
    val error: Error? = null,
) {
    sealed class Error {
        data object LoadingPhotoError : Error()
    }
}