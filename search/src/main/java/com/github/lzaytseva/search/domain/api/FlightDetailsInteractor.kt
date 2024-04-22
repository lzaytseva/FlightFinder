package com.github.lzaytseva.search.domain.api

import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.util.Resource

internal interface FlightDetailsInteractor {

    suspend fun getTicketOffers(): Resource<List<TicketOffer>>
}