package com.github.lzaytseva.search.di

import android.content.Context
import android.content.SharedPreferences
import com.github.lzaytseva.search.data.network.api.ApiService
import com.github.lzaytseva.search.data.network.api.SearchRemoteDataSource
import com.github.lzaytseva.search.data.network.impl.SearchMockDataSource
import com.github.lzaytseva.search.data.network.impl.SearchRemoteDataSourceImpl
import com.github.lzaytseva.search.data.repository.SearchRepositoryImpl
import com.github.lzaytseva.search.data.storage.LastPlaceStorage
import com.github.lzaytseva.search.data.storage.LastPlaceStorageImpl
import com.github.lzaytseva.search.domain.api.SearchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal val dataModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(
            searchRemoteDataSource = get(),
            searchMockDataSource = get(),
            lastPlaceStorage = get()
        )
    }

    single<SearchRemoteDataSource> {
        SearchRemoteDataSourceImpl(
            context = androidContext(),
            apiService = get()
        )
    }

    single {
        SearchMockDataSource(context = androidContext())
    }

    single<LastPlaceStorage> {
        LastPlaceStorageImpl(
            sharedPreferences = get()
        )
    }

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single<SharedPreferences> {
        androidContext()
            .getSharedPreferences(FLIGHT_FINDER_PREFERENCES, Context.MODE_PRIVATE)
    }
}

private const val BASE_URL = "https://drive.google.com/"
private const val FLIGHT_FINDER_PREFERENCES = "flight_finder_prefs"