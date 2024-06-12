package com.example.mobilne2.quizScreen.repository

import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class QuizRepository @Inject constructor(
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

    suspend fun getCatID(imageID: String): String {
        return database.catProfileDao().getCatID(imageID)
    }

}