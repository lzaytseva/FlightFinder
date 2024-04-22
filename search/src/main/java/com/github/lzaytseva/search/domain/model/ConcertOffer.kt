package com.github.lzaytseva.search.domain.model

internal data class ConcertOffer(
    val id: Int,
    val title: String,
    val city: String,
    val imageResId: Int,
    val price: String
)
