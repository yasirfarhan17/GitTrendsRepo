package com.example.gittrendsrepo.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.networkmodule.api.GithubApi
import com.example.networkmodule.network.NetworkClient
import com.example.networkmodule.network.NetworkManager
import com.example.networkmodule.repository.GitTrendsRepo
import com.example.networkmodule.repository.GitTrendsRepoImpl
import com.example.networkmodule.usecase.GitHubUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        NetworkClient.provideRetrofit(
            okHttpClient)

    @Singleton
    @Provides
    fun provideOkHttp(networkManager: NetworkManager):OkHttpClient{
        return NetworkClient.provideOkHttp(networkManager)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ): NetworkManager {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return NetworkManager(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideTrendingApi(retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideTrendingRepo(api:GithubApi): GitTrendsRepo =
         GitTrendsRepoImpl(api)
    @Provides
    @Singleton
    fun provideTrendingUseCase(repo: GitTrendsRepo): GitHubUseCase =
        GitHubUseCase(repo)

}