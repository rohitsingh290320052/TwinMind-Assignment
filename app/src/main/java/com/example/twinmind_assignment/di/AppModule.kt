package com.example.twinmind_assignment.di

import com.example.twinmind_assignment.data.AudioRecorder
import com.example.twinmind_assignment.data.RemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecorder(app: android.app.Application) =
        AudioRecorder(app)

    @Provides
    @Singleton
    fun provideRemoteApi(): RemoteApi =
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteApi::class.java)
}
