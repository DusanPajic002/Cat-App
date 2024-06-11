package com.example.mobilne2.catProfile.api.di

import com.example.mobilne2.catListP.api.CatApi
import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.catProfile.api.model.CatImageApiModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatsPrifleModule {
    @Provides
    @Singleton
    fun provideCatProfileApi(retrofit: Retrofit): CatProfileApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCatImagesApi(retrofit: Retrofit): CatImageApiModel = retrofit.create()
}