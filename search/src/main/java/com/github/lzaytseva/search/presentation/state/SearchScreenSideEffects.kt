package com.github.lzaytseva.search.presentation.state

internal sealed interface SearchScreenSideEffects {

    data class Error(val message: String) : SearchScreenSideEffects

    data class OpenFlightDetailsScreen(val from: String, val to: String): SearchScreenSideEffects
}