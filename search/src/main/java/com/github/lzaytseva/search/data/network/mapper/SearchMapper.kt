package com.github.lzaytseva.search.data.network.mapper

import com.github.lzaytseva.search.data.network.dto.ConcertOfferDto
import com.github.lzaytseva.search.data.network.dto.TicketDto
import com.github.lzaytseva.search.data.network.dto.TicketOfferDto
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.uikit.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.round

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
    hasTransfer = hasTransfer,
    travelTime = countTravelTime(arrival.date, departure.date)
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

private fun String.getTimeFromDate() = substringAfter('T').substringBeforeLast(":")


private fun getConcertOfferImageResId(id: Int): Int {
    return when (id) {
        1 -> R.drawable.image_11
        2 -> R.drawable.image_6
        3 -> R.drawable.image_2
        else -> 0
    }
}

private fun countTravelTime(arrival: String, departure: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val startDate = format.parse(departure)
    val endDate = format.parse(arrival)

    val durationMillis = endDate.time - startDate.time
    val hours = durationMillis.toDouble() / (1000 * 60 * 60)
    return roundToHalf(hours).toString()
}

private fun roundToHalf(number: Double): Double {
    return round(number * 2.0) / 2.0
}