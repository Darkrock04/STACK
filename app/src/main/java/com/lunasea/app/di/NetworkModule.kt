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

/**
 * NetworkModule - Dagger Hilt module for network-related dependencies
 * 
 * This module provides all network-related dependencies for the application,
 * including HTTP clients, Retrofit instances, and API interfaces. It uses
 * Dagger Hilt's dependency injection to manage these dependencies.
 * 
 * Key responsibilities:
 * - Provide OkHttpClient with logging configuration
 * - Create Retrofit instances for Sonarr and Radarr APIs
 * - Provide API interface implementations
 * - Configure JSON serialization/deserialization
 * - Set up base URLs for different services
 * 
 * Architecture:
 * - Uses Dagger Hilt for dependency injection
 * - Implements Module pattern for dependency provision
 * - Uses @Singleton for shared instances
 * - Uses @Named annotations for multiple instances of same type
 * - Installs in SingletonComponent for app-wide availability
 * 
 * Configuration:
 * - HTTP logging enabled for debugging
 * - Gson converter for JSON handling
 * - Default base URLs for local development
 * - Separate Retrofit instances for different services
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * provideOkHttpClient - Provides a configured OkHttpClient instance
     * 
     * This method creates and configures an OkHttpClient with logging capabilities.
     * The client is used by all Retrofit instances for HTTP communication.
     * 
     * @return OkHttpClient - Configured HTTP client with logging interceptor
     * 
     * What it does:
     * 1. Creates HttpLoggingInterceptor with BODY level logging
     * 2. Builds OkHttpClient with the logging interceptor
     * 3. Returns the configured client
     * 
     * Configuration details:
     * - Logging level set to BODY for complete request/response logging
     * - Interceptor added to log all HTTP traffic
     * - Client configured for both development and production use
     * 
     * Use cases:
     * - Used by all Retrofit instances
     * - Provides HTTP logging for debugging
     * - Handles network requests and responses
     * - Manages connection pooling and timeouts
     * 
     * Why it's important:
     * - Centralizes HTTP client configuration
     * - Enables debugging through logging
     * - Provides consistent network behavior
     * - Allows easy modification of network settings
     */
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

    /**
     * provideSonarrRetrofit - Provides a Retrofit instance configured for Sonarr API
     * 
     * This method creates a Retrofit instance specifically configured for communicating
     * with Sonarr servers. It uses the default Sonarr port and API version.
     * 
     * @param okHttpClient OkHttpClient - The HTTP client to use for requests
     * @return Retrofit - Configured Retrofit instance for Sonarr API
     * 
     * What it does:
     * 1. Creates Retrofit.Builder instance
     * 2. Sets base URL to default Sonarr endpoint (localhost:8989)
     * 3. Configures Gson converter for JSON handling
     * 4. Uses the provided OkHttpClient
     * 5. Returns the configured Retrofit instance
     * 
     * Configuration details:
     * - Base URL: "http://localhost:8989/api/v3/"
     * - JSON converter: GsonConverterFactory
     * - HTTP client: Provided OkHttpClient with logging
     * - API version: v3 (latest stable)
     * 
     * Use cases:
     * - Used to create SonarrApi interface implementation
     * - Handles all Sonarr-related API calls
     * - Provides type-safe API communication
     * - Enables JSON serialization/deserialization
     * 
     * Why it's important:
     * - Provides dedicated Retrofit instance for Sonarr
     * - Ensures correct API version and base URL
     * - Enables type-safe API communication
     * - Supports JSON data exchange
     */
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

    /**
     * provideRadarrRetrofit - Provides a Retrofit instance configured for Radarr API
     * 
     * This method creates a Retrofit instance specifically configured for communicating
     * with Radarr servers. It uses the default Radarr port and API version.
     * 
     * @param okHttpClient OkHttpClient - The HTTP client to use for requests
     * @return Retrofit - Configured Retrofit instance for Radarr API
     * 
     * What it does:
     * 1. Creates Retrofit.Builder instance
     * 2. Sets base URL to default Radarr endpoint (localhost:7878)
     * 3. Configures Gson converter for JSON handling
     * 4. Uses the provided OkHttpClient
     * 5. Returns the configured Retrofit instance
     * 
     * Configuration details:
     * - Base URL: "http://localhost:7878/api/v3/"
     * - JSON converter: GsonConverterFactory
     * - HTTP client: Provided OkHttpClient with logging
     * - API version: v3 (latest stable)
     * 
     * Use cases:
     * - Used to create RadarrApi interface implementation
     * - Handles all Radarr-related API calls
     * - Provides type-safe API communication
     * - Enables JSON serialization/deserialization
     * 
     * Why it's important:
     * - Provides dedicated Retrofit instance for Radarr
     * - Ensures correct API version and base URL
     * - Enables type-safe API communication
     * - Supports JSON data exchange
     */
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

    /**
     * provideSonarrApi - Provides SonarrApi interface implementation
     * 
     * This method creates an implementation of the SonarrApi interface using
     * the Sonarr-configured Retrofit instance. This is the main entry point
     * for all Sonarr API operations.
     * 
     * @param retrofit Retrofit - The Sonarr-configured Retrofit instance
     * @return SonarrApi - Implementation of SonarrApi interface
     * 
     * What it does:
     * 1. Uses the provided Retrofit instance
     * 2. Creates implementation of SonarrApi interface
     * 3. Returns the API implementation
     * 
     * Use cases:
     * - Injected into SonarrRepository
     * - Used for all Sonarr API calls
     * - Provides type-safe API methods
     * - Handles HTTP communication with Sonarr
     * 
     * Why it's important:
     * - Provides concrete implementation of SonarrApi
     * - Enables dependency injection of API interface
     * - Ensures type safety for API calls
     * - Abstracts HTTP communication details
     */
    @Provides
    @Singleton
    fun provideSonarrApi(@Named("sonarr") retrofit: Retrofit): SonarrApi {
        return retrofit.create(SonarrApi::class.java)
    }

    /**
     * provideRadarrApi - Provides RadarrApi interface implementation
     * 
     * This method creates an implementation of the RadarrApi interface using
     * the Radarr-configured Retrofit instance. This is the main entry point
     * for all Radarr API operations.
     * 
     * @param retrofit Retrofit - The Radarr-configured Retrofit instance
     * @return RadarrApi - Implementation of RadarrApi interface
     * 
     * What it does:
     * 1. Uses the provided Retrofit instance
     * 2. Creates implementation of RadarrApi interface
     * 3. Returns the API implementation
     * 
     * Use cases:
     * - Injected into RadarrRepository
     * - Used for all Radarr API calls
     * - Provides type-safe API methods
     * - Handles HTTP communication with Radarr
     * 
     * Why it's important:
     * - Provides concrete implementation of RadarrApi
     * - Enables dependency injection of API interface
     * - Ensures type safety for API calls
     * - Abstracts HTTP communication details
     */
    @Provides
    @Singleton
    fun provideRadarrApi(@Named("radarr") retrofit: Retrofit): RadarrApi {
        return retrofit.create(RadarrApi::class.java)
    }
} 