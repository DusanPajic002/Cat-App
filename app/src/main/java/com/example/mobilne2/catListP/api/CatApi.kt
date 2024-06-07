package com.example.mobilne2.catListP.api

import com.example.mobilne2.catListP.api.model.CatApiModel
import retrofit2.http.GET

interface CatApi {

    @GET("breeds")
    suspend fun getAllCats(): List<CatApiModel>

}