package com.example.mobilne2.catProfile.aGallery.gallery

import com.example.mobilne2.catProfile.profile.model.CatImageUI
import com.example.mobilne2.catProfile.profile.model.CatProfileUI

data class CatGalleryState (
    val catId: String = "",
    val fetching: Boolean = false,
    val album: List<CatImageUI> = emptyList(),
    val error: DetailsError? = null,
) {
    sealed class DetailsError {
        data class DataUpdateFailed(val cause: Throwable? = null) : DetailsError()
    }
}