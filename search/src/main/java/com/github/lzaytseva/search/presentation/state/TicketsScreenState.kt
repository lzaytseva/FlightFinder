package com.github.lzaytseva.search.presentation.state

import com.github.lzaytseva.search.domain.model.Ticket

internal sealed interface TicketsScreenState {

    data object Initial : TicketsScreenState

    data class Content(
        val tickets: List<Ticket>
    ) : TicketsScreenState
}