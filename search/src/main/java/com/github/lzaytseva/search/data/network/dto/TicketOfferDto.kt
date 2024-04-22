package com.github.lzaytseva.search.data.network.dto

import com.google.gson.annotations.SerializedName

internal data class TicketOfferDto(
    val id: Int,
    val price: PriceDto,
    @SerializedName("time_range") val timeRange: List<String>,
    val title: String
)