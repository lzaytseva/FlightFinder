package com.github.lzaytseva.search.presentation.state

import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.PlaceOffer

internal sealed interface SearchScreenState {

    data object Initial : SearchScreenState

    data class Content(
        val lastDeparturePlace: String,
        val concertOffers: List<ConcertOffer>,
        val destinationOffers: List<PlaceOffer>
    ) : SearchScreenState
}