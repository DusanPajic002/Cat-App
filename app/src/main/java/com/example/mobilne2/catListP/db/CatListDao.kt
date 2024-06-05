package com.example.domaci2.catListP.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<Cat>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(cat: Cat)

    @Query("SELECT * FROM Cat")
    suspend fun getAll(): List<Cat>

    @Query("SELECT * FROM Cat")
    fun observeAll(): Flow<List<Cat>>

}