package com.github.lzaytseva.search.domain.api

import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.util.Resource

internal interface SearchInteractor {

    suspend fun getConcertOffers(): Resource<List<ConcertOffer>>

    fun getDestinationRecommendations(): List<PlaceOffer>

    fun getLastDeparturePlace(): String?

    fun saveLastDeparturePlace(place: String)
}