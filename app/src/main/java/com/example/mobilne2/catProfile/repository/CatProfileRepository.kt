package com.example.mobilne2.catProfile.repository

import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class CatProfileRepository @Inject constructor(
    private val catProfileApi: CatProfileApi,
    private val database: AppDatabase,
) {

    suspend fun getCats(id: String): Cat {
        return database.catProfileDao().get(id)
    }
}