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

    suspend fun getAllImagesCatID(catID: String): List<CatImages> {
        return database.catProfileDao().getImagesCatID(catID)
    }

    suspend fun getImagesByID(id: String): CatImages {
        return database.catProfileDao().getImagesByID(id)
    }

    suspend fun fetchImages(catId: String){
        val images = catsApi.getAllImages(breed_ids = catId).map { it.asCatImageModel(catId) }
        println("Imagess: $images")
        database.catProfileDao().insertAllImages(images)
        println("Imagessssss: $images")
    }

    suspend fun getCatID(imageID: String): String {
        return database.catProfileDao().getCatID(imageID)
    }

}