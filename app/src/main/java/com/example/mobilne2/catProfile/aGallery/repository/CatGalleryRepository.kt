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
        println("---------dsa-" + catId)
        val images = catsApi.getAllImages(catId = catId)
        println("---------dsa-" + images)
        println("---------dsa-" + images.size)
        //database.catProfileDao().insertAllImages(images)
//        database.catProfileDao().insertAllImages(
//            catsApi.getAllImages(catId = catID).map { it.asCatImageModel(catID) }
//        )
    }

    suspend fun getCatID(imageID: String): String {
        return database.catProfileDao().getCatID(imageID)
    }

}