package com.example.data.di

import com.example.data.network.Api
import com.example.data.network.MyCacheInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient {
        val cacheDir = File("http_cache")
        val cache = Cache(cacheDir, CACHE_SIZE)
        val interceptor = MyCacheInterceptor()
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addNetworkInterceptor(interceptor = interceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    companion object {
        private const val TIMEOUT = 60L
        private const val BASE_URL = "http://10.0.2.2:8080"
        private const val CACHE_SIZE = 10L * 1024L * 1024L //10MiB
    }
}