package com.github.lzaytseva.search.domain.impl

import com.github.lzaytseva.search.domain.api.FlightDetailsInteractor
import com.github.lzaytseva.search.domain.api.FlightDetailsRepository
import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.util.Resource

internal class FlightDetailsInteractorImpl(
    private val repository: FlightDetailsRepository
) : FlightDetailsInteractor {
    override suspend fun getTicketOffers(): Resource<List<TicketOffer>> {
        return repository.getTicketsOffers()
    }
}