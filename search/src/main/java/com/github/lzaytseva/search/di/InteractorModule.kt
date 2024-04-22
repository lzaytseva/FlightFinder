package com.github.lzaytseva.search.di

import com.github.lzaytseva.search.domain.api.SearchInteractor
import com.github.lzaytseva.search.domain.impl.SearchInteractorImpl
import org.koin.dsl.module

internal val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }
}