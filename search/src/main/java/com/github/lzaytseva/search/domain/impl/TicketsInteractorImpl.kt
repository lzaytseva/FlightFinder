package com.github.lzaytseva.search.domain.impl

import com.github.lzaytseva.search.domain.api.TicketsInteractor
import com.github.lzaytseva.search.domain.api.TicketsRepository
import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.util.Resource

internal class TicketsInteractorImpl(
    private val repository: TicketsRepository
) : TicketsInteractor {
    override suspend fun getTickets(): Resource<List<Ticket>> {
        return repository.getTickets()
    }
}