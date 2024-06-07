package com.example.mobilne2.catProfile.api

import com.example.mobilne2.catListP.api.model.CatApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CatProfileApi {

    @GET("breeds/{breedId}")
    suspend fun getCat(@Path("breedId") catId: String): CatApiModel

//    @GET("images/{imageId}")
//    suspend fun get_image_id(@Path("imageId") imageId: String): ImageApiModel
}