package com.github.lzaytseva.search.di

import com.github.lzaytseva.search.presentation.viewmodel.FlightDetailsViewModel
import com.github.lzaytseva.search.presentation.viewmodel.SearchViewModel
import com.github.lzaytseva.search.presentation.viewmodel.TicketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    viewModel {
        SearchViewModel(
            searchInteractor = get()
        )
    }

    viewModel {
        FlightDetailsViewModel(
            flightDetailsInteractor = get()
        )
    }

    viewModel {
        TicketsViewModel(
            ticketsInteractor = get()
        )
    }
}