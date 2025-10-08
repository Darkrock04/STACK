package com.stack.app.di

import com.stack.app.data.api.RadarrApi
import com.stack.app.data.api.SonarrApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("sonarr")
    fun provideSonarrRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8989/api/v3/") // Default Sonarr URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("radarr")
    fun provideRadarrRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:7878/api/v3/") // Default Radarr URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSonarrApi(@Named("sonarr") retrofit: Retrofit): SonarrApi {
        return retrofit.create(SonarrApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRadarrApi(@Named("radarr") retrofit: Retrofit): RadarrApi {
        return retrofit.create(RadarrApi::class.java)
    }
} 