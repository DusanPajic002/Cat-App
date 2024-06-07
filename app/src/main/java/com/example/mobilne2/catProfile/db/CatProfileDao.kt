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
//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    suspend fun insertImages(catImages: CatImages)

}