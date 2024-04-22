package com.github.lzaytseva.search.domain.model

internal data class TicketOffer(
    val id: Int,
    val price: String,
    val timeRange: String,
    val title: String
)