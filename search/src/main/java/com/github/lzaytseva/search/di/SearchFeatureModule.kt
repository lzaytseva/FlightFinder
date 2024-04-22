package com.github.lzaytseva.search.di

import org.koin.dsl.module

val searchFeatureModule = module {
    includes(dataModule, interactorModule, viewModelModule)
}