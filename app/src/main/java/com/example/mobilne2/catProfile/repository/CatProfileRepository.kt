package com.example.mobilne2.catProfile.repository

import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.mapper.asCatImageModel
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class CatProfileRepository @Inject constructor(
    private val catProfileApi: CatProfileApi,
    private val catsApi: CatProfileApi,
    private val database: AppDatabase,
) {

    suspend fun getCats(id: String): Cat {
        return database.catProfileDao().get(id)
    }

    suspend fun getImages(id: String): CatImages {
        return database.catProfileDao().getImages(id)
    }
    suspend fun fetchImages(id: String, catID: String){
        val images = catsApi.get_image_id(id)
        database.catProfileDao().insertImages(catImages = images.asCatImageModel(catID))
    }


}