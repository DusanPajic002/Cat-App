package com.example.domaci2.catListP.api

import com.example.domaci2.catListP.api.model.CatApiModel
import com.example.domaci2.breeds.ImageApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CatApi {

    @GET("breeds")
    suspend fun getAllCats(): List<CatApiModel>
    @GET("breeds/{breedId}")
    suspend fun getCat(@Path("breedId") catId: String): CatApiModel
    @GET("images/{imageId}")
    suspend fun get_image_id(@Path("imageId") imageId: String): ImageApiModel

}