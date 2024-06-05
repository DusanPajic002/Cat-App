package com.example.domaci2.catListP.repository

import com.example.domaci2.catListP.api.CatApi
import com.example.domaci2.catListP.api.model.CatApiModel
import com.example.domaci2.breeds.ImageApiModel
import com.example.domaci2.catListP.mappers.asCatDbModel
import com.example.domaci2.database.DataBase
import rs.edu.raf.rma6.networking.retrofit

object Repository {

    private val database by lazy { DataBase.database }

    private val catApi: CatApi by lazy { retrofit.create(CatApi::class.java) }

    private val catsApi: CatApi = retrofit.create(CatApi::class.java)
    suspend fun fetchAllCats() {
        val cats = catsApi.getAllCats()
        database.catListDao().insertAll(cats = cats.map { it.asCatDbModel() })
    }
    suspend fun fetchCat(catId: String): CatApiModel {
        val cat = catsApi.getCat(catId)
        return cat
    }
    suspend fun fetchCatImages(imageId: String): ImageApiModel {
        return catsApi.get_image_id(imageId)
    }


}