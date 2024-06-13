package com.example.mobilne2.homePage

import com.example.mobilne2.catListP.db.Cat

data class HomeState (
    var fetching: Boolean = false,
    var fetchedCats: Boolean = true,
    var fetching2: Boolean = false,
    var cats: List<Cat> = emptyList(),
    var error: DetailsError? = null
) {

    sealed class DetailsError {
        data class DataUpdateFailed(val cause: Throwable) : DetailsError()
    }


}