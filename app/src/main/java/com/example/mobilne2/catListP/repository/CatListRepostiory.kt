package com.example.mobilne2.catListP.repository

import com.example.mobilne2.catListP.api.CatApi
import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catListP.mappers.asCatDbModel
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class CatListRepostiory @Inject constructor(
    private val catsApi: CatApi,
    private val database: AppDatabase,
) {

    suspend fun fetchAllCats() : Boolean{
        val cats = catsApi.getAllCats()
        database.catListDao().insertAll(cats = cats.map { it.asCatDbModel() })
        return true;
    }
    suspend fun fetchAllImages() {
        val cats = catsApi.getAllCats()
        database.catListDao().insertAll(cats = cats.map { it.asCatDbModel() })
    }

    suspend fun getAllCats(): List<Cat> {
        return database.catListDao().getAll()
    }

}
