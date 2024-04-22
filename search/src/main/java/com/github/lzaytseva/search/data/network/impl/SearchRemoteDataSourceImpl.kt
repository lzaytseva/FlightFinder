package com.github.lzaytseva.search.data.network.impl

import android.content.Context
import com.github.lzaytseva.search.data.network.api.ApiService
import com.github.lzaytseva.search.data.network.api.SearchRemoteDataSource
import com.github.lzaytseva.search.data.network.dto.AllTicketsResponse
import com.github.lzaytseva.search.data.network.dto.ConcertsOfferResponse
import com.github.lzaytseva.search.data.network.dto.TicketsOfferResponse
import com.github.lzaytseva.util.ConnectionChecker

internal class SearchRemoteDataSourceImpl(
    private val context: Context,
    private val apiService: ApiService
) : SearchRemoteDataSource {
    override suspend fun getConcertsOffer(): ConcertsOfferResponse {
        checkConnection()
        return apiService.getConcertsOffer()
    }

    override suspend fun getTicketsOffer(): TicketsOfferResponse {
        checkConnection()
        return apiService.getTicketsOffer()
    }

    override suspend fun getAllTickets(): AllTicketsResponse {
        checkConnection()
        return apiService.getAllTickets()
    }

    private fun checkConnection() {
        if (!ConnectionChecker.isConnected(context)) {
            throw RuntimeException("No internet")
        }
    }
}