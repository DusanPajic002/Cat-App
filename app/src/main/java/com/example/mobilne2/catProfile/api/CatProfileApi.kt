package com.example.mobilne2.catProfile.api

import com.example.mobilne2.catProfile.api.model.CatImageApiModel
import com.example.mobilne2.catProfile.db.CatImages
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatProfileApi {

    @GET("images/{id}")
    suspend fun get_image_id(@Path("id") imageID: String): CatImageApiModel

    @GET("images/search")
    suspend fun getAllImages(
        @Query("limit") limit: Int = 10,
        @Query("breed_ids") breed_ids: String
    ): List<CatImageApiModel>

}