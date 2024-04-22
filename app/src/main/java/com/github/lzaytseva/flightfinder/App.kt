package com.github.lzaytseva.flightfinder

import android.app.Application
import com.github.lzaytseva.search.di.searchFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidContext(this@App)
            modules(searchFeatureModule)
        }
    }
}