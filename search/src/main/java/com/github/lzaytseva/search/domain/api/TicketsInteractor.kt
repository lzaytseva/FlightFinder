package com.github.lzaytseva.search.domain.api

import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.util.Resource

internal interface TicketsInteractor {

    suspend fun getTickets(): Resource<List<Ticket>>
}