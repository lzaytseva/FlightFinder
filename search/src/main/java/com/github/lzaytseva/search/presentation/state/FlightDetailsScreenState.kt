package com.github.lzaytseva.search.presentation.state

import com.github.lzaytseva.search.domain.model.TicketOffer

internal sealed interface FlightDetailsScreenState {

    data object Initial : FlightDetailsScreenState

    data class Content(
        val ticketOffers: List<TicketOffer>
    ) : FlightDetailsScreenState
}