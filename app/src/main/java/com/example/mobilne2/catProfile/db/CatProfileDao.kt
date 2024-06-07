package com.example.mobilne2.catProfile.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.mobilne2.catListP.db.Cat

@Dao
interface CatProfileDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertImages(catImages: CatImages)
}