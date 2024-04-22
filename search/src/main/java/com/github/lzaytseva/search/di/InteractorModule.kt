package com.github.lzaytseva.search.di

import com.github.lzaytseva.search.domain.api.FlightDetailsInteractor
import com.github.lzaytseva.search.domain.api.SearchInteractor
import com.github.lzaytseva.search.domain.api.TicketsInteractor
import com.github.lzaytseva.search.domain.impl.FlightDetailsInteractorImpl
import com.github.lzaytseva.search.domain.impl.SearchInteractorImpl
import com.github.lzaytseva.search.domain.impl.TicketsInteractorImpl
import org.koin.dsl.module

internal val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }

    single<FlightDetailsInteractor> {
        FlightDetailsInteractorImpl(repository = get())
    }

    single<TicketsInteractor> {
        TicketsInteractorImpl(repository = get())
    }
}