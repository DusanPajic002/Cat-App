package com.example.mobilne2.catListP.api.di

import com.example.domaci2.catListP.api.CatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatsModule {
    @Provides
    @Singleton
    fun provideCatApi(retrofit: Retrofit): CatApi = retrofit.create()
}