package com.example.mobilne2.catProfile.aGallery.repository

import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.mapper.asCatImageModel
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class CatGalleryRepository @Inject constructor(
    private val catsApi: CatProfileApi,
    private val database: AppDatabase,
) {


    suspend fun getAllImagesCatID(id: String): List<CatImages> {
        return database.catProfileDao().getImagesCatID(id)
    }

    suspend fun getImagesByID(id: String): CatImages {
        return database.catProfileDao().getImagesByID(id)
    }

    suspend fun fetchImages(catID: String){
        val images = catsApi.getAllImages(100, catID)
        database.catProfileDao().insertAllImages(catImages = images.map { it.asCatImageModel(catID) })
    }
}