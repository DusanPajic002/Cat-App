package com.example.mobilne2.quizScreen.repository

import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.mapper.asCatImageModel
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val catProfileApi: CatProfileApi,
    private val database: AppDatabase,
) {
    suspend fun getCat(id: String): Cat {
        return database.catProfileDao().get(id)
    }
    suspend fun getAllCats(): List<Cat> {
        return database.catListDao().getAll()
    }
    suspend fun getImagesByID(id: String): CatImages {
        return database.catProfileDao().getImagesByID(id)
    }

    suspend fun getAllImagesCatID(catID: String): List<CatImages> {
        return database.catProfileDao().getImagesCatID(catID)
    }

    suspend fun featchAllImagesCatID(catID: String){
        val images = catProfileApi.getAllImages(100, catID)
        database.catProfileDao().insertAllImages(catImages = images.map { it.asCatImageModel(catID) })
    }

    suspend fun getCatID(imageID: String): String {
        return database.catProfileDao().getCatID(imageID)
    }

}