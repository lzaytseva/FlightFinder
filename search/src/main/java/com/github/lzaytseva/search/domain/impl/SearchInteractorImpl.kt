package com.github.lzaytseva.search.domain.impl

import com.github.lzaytseva.search.domain.api.SearchInteractor
import com.github.lzaytseva.search.domain.api.SearchRepository
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.util.Resource

internal class SearchInteractorImpl(
    private val repository: SearchRepository
) : SearchInteractor {
    override suspend fun getConcertOffers(): Resource<List<ConcertOffer>> {
        return repository.getConcertOffers()
    }

    override fun getDestinationRecommendations(): List<PlaceOffer> {
        return repository.getDestinationRecommendations()
    }

    override fun getLastDeparturePlace(): String? {
        return repository.getLastDeparturePlace()
    }

    override fun saveLastDeparturePlace(place: String) {
        repository.saveLastDeparturePlace(place)
    }
}