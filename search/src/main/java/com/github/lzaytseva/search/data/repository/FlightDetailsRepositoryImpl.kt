package com.github.lzaytseva.search.data.repository

import com.github.lzaytseva.search.data.network.api.SearchRemoteDataSource
import com.github.lzaytseva.search.data.network.mapper.toDomain
import com.github.lzaytseva.search.domain.api.FlightDetailsRepository
import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.util.Resource

internal class FlightDetailsRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : FlightDetailsRepository {

    override suspend fun getTicketsOffers(): Resource<List<TicketOffer>> {
        return try {
            val response = searchRemoteDataSource.getTicketsOffer()
            Resource.Success(response.ticketsOffer.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}