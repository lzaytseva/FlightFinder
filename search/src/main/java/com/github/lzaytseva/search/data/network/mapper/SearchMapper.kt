package com.github.lzaytseva.search.data.network.mapper

import com.github.lzaytseva.search.data.network.dto.ConcertOfferDto
import com.github.lzaytseva.search.data.network.dto.TicketDto
import com.github.lzaytseva.search.data.network.dto.TicketOfferDto
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.uikit.R
import java.text.NumberFormat
import java.util.Locale

internal fun TicketOfferDto.toDomain() = TicketOffer(
    id = id,
    timeRange = timeRange.joinToString(" "),
    title = title,
    price = price.value.formatPrice()
)

internal fun TicketDto.toDomain() = Ticket(
    id = id,
    price = price.value.formatPrice(),
    arrivalTime = arrival.date.getTimeFromDate(),
    departureTime = departure.date.getTimeFromDate(),
    departureAirportCode = departure.airport,
    arrivalAirportCode = arrival.airport,
    badge = badge,
    hasTransfer = hasTransfer
)

internal fun ConcertOfferDto.toDomain() = ConcertOffer(
    id = id,
    title = title,
    city = town,
    price = price.value.formatPrice(),
    imageResId = getConcertOfferImageResId(id)
)

private fun Int.formatPrice(): String {
    val format = NumberFormat.getInstance(Locale.US)
    return format.format(this).replace(",", " ")
}

private fun String.getTimeFromDate() = substringAfter(':')

private fun getConcertOfferImageResId(id: Int): Int {
    return when (id) {
        1 -> R.drawable.image_11
        2 -> R.drawable.image_6
        3 -> R.drawable.image_2
        else -> 0
    }
}