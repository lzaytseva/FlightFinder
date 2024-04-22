package com.github.lzaytseva.search.domain.model

internal data class Ticket(
    val arrivalTime: String,
    val arrivalAirportCode: String,
    val departureAirportCode: String,
    val badge: String,
    val departureTime: String,
    val hasTransfer: Boolean,
    val id: Int,
    val price: String,
    val travelTime: String
)