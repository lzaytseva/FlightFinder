package com.github.lzaytseva.search.data.repository

import com.github.lzaytseva.search.data.network.api.SearchRemoteDataSource
import com.github.lzaytseva.search.data.network.mapper.toDomain
import com.github.lzaytseva.search.domain.api.TicketsRepository
import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.util.Resource

internal class TicketsRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : TicketsRepository {
    override suspend fun getTickets(): Resource<List<Ticket>> {
        return try {
            val response = searchRemoteDataSource.getAllTickets()
            Resource.Success(response.tickets.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}