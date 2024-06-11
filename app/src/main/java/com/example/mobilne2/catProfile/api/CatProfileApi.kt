package com.example.mobilne2.catProfile.api

import com.example.mobilne2.catProfile.api.model.CatImageApiModel
import com.example.mobilne2.catProfile.db.CatImages
import retrofit2.http.GET
import retrofit2.http.Path

interface CatProfileApi {
//
//    @GET("breeds/{breedId}")
//    suspend fun getCat(@Path("breedId") catId: String): CatApiModel

    @GET("images/{id}")
    suspend fun get_image_id(@Path("id") imageID: String): CatImageApiModel
}