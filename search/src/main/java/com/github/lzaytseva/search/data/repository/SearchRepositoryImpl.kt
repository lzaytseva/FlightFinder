package com.github.lzaytseva.search.data.repository

import com.github.lzaytseva.search.data.network.api.SearchRemoteDataSource
import com.github.lzaytseva.search.data.network.impl.SearchMockDataSource
import com.github.lzaytseva.search.data.network.mapper.toDomain
import com.github.lzaytseva.search.data.storage.LastPlaceStorage
import com.github.lzaytseva.search.domain.api.SearchRepository
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.util.Resource

internal class SearchRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchMockDataSource: SearchMockDataSource,
    private val lastPlaceStorage: LastPlaceStorage
) : SearchRepository {
    override suspend fun getConcertOffers(): Resource<List<ConcertOffer>> {
        return try {
            val response = searchRemoteDataSource.getConcertsOffer()
            Resource.Success(response.offers.map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override fun getDestinationRecommendations(): List<PlaceOffer> {
        return searchMockDataSource.getDestinationRecommendations()
    }

    override fun getLastDeparturePlace(): String? {
        return lastPlaceStorage.get()
    }

    override fun saveLastDeparturePlace(place: String) {
        lastPlaceStorage.save(place)
    }
}