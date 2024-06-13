package com.example.mobilne2.leaderBoardP.api.di

import com.example.mobilne2.leaderBoardP.api.LeaderBoardApi
import com.example.mobilne2.networking.LeaderboardApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LeaderBoardModule {
    @Provides
    @Singleton
    fun provideLeaderBoardApi(@LeaderboardApiClient retrofit: Retrofit): LeaderBoardApi = retrofit.create()
}