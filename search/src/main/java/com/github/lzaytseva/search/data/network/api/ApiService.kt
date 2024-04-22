package com.github.lzaytseva.search.data.network.api

import com.github.lzaytseva.search.data.network.dto.AllTicketsResponse
import com.github.lzaytseva.search.data.network.dto.ConcertsOfferResponse
import com.github.lzaytseva.search.data.network.dto.TicketsOfferResponse
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface ApiService {


    @GET("/uc?export=download&id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav")
    suspend fun getConcertsOffer(): ConcertsOfferResponse

    @GET("/uc?export=download&id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta")
    suspend fun getTicketsOffer(): TicketsOfferResponse

    @GET("/uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
    suspend fun getAllTickets(): AllTicketsResponse
}