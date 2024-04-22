package com.github.lzaytseva.search.presentation.state

internal sealed interface FlightDetailsScreenSideEffects {

    data class Error(val message: String) : FlightDetailsScreenSideEffects
}