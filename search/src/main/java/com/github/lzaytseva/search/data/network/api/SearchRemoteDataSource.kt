package com.github.lzaytseva.search.data.network.api

import com.github.lzaytseva.search.data.network.dto.AllTicketsResponse
import com.github.lzaytseva.search.data.network.dto.ConcertsOfferResponse
import com.github.lzaytseva.search.data.network.dto.TicketsOfferResponse
import retrofit2.http.GET

internal interface SearchRemoteDataSource {

    suspend fun getConcertsOffer(): ConcertsOfferResponse

    suspend fun getTicketsOffer(): TicketsOfferResponse

    suspend fun getAllTickets(): AllTicketsResponse
}