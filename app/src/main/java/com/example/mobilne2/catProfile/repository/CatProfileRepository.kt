package com.example.mobilne2.catProfile.repository

import com.example.mobilne2.catListP.mappers.asCatDbModel
import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.database.AppDatabase
import javax.inject.Inject

class CatProfileRepository @Inject constructor(
    private val catProfileApi: CatProfileApi,
    private val database: AppDatabase,
) {


}