package com.example.mobilne2.catProfile.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobilne2.catListP.db.Cat

@Dao
interface CatProfileDao {

    @Query("SELECT * FROM Cat WHERE id = :id")
    suspend fun get(id: String): Cat

    @Query("SELECT catId FROM CatImages WHERE id = :imageID")
    suspend fun getCatID(imageID: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImages(catImages: List<CatImages>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(catImages: CatImages)

    @Query("SELECT * FROM CatImages WHERE catId = :catId")
    suspend fun getImagesCatID(catId: String): List<CatImages>

    @Query("SELECT * FROM CatImages WHERE id = :id")
    suspend fun getImagesByID(id: String): CatImages

}