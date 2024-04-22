package com.github.lzaytseva.search.data.network.dto

import com.google.gson.annotations.SerializedName

internal data class TicketDto(
    val arrival: ArrivalDto,
    val badge: String?,
    val departure: DepartureDto,
    @SerializedName("has_transfer") val hasTransfer: Boolean,
    val id: Int,
    val price: PriceDto,
)