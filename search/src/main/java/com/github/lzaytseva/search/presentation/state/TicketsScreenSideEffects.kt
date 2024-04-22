package com.github.lzaytseva.search.presentation.state

internal sealed interface TicketsScreenSideEffects {

    data class Error(val message: String) : TicketsScreenSideEffects
}