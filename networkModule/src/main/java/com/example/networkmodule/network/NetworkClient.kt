package com.example.networkmodule.network

import com.example.networkmodule.api.GithubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    private const val TIMEOUT_IN_SECONDS=20L
    private const val CONTENT_TYPE_APPLICATION="application/json"

    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(GithubApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun provideOkHttp(
        networkManager:NetworkManager
    ): OkHttpClient {
        val httpBuilder= OkHttpClient.Builder()
        httpBuilder.apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level= HttpLoggingInterceptor.Level.BODY
            })
        }
        httpBuilder.apply {
            addInterceptor(NoInternetInterceptor(networkManager))
            connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        }
        return httpBuilder.build()
    }
}