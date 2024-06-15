package com.example.mobilne2.catProfile.aGallery.gallery

import com.example.mobilne2.catProfile.profile.model.CatImageUI
import com.example.mobilne2.catProfile.profile.model.CatProfileUI

data class CatGalleryState (
    val fetching: Boolean = false,
    val page : Int = 1,
    val maxPage : Int = 1,
    val dataPerPage : Int = 20,
    val album: List<CatImageUI> = emptyList(),
    val catsPerPage: List<CatImageUI> = emptyList(),
    val error: DetailsError? = null,
) {
    sealed class Events {
        data class changePage(val changePage: Int) : Events()
    }
    sealed class DetailsError {
        data class DataUpdateFailed(val cause: Throwable? = null) : DetailsError()
    }
}