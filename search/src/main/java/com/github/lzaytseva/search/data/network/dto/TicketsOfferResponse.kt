package com.github.lzaytseva.search.data.network.dto

import com.google.gson.annotations.SerializedName

internal data class TicketsOfferResponse(
    @SerializedName("tickets_offers") val ticketsOffer: List<TicketOfferDto>
)