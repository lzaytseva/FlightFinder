package com.github.lzaytseva.search.data.network.impl

import android.content.Context
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.uikit.R

internal class SearchMockDataSource(
    context: Context
) {

    private val destinationRecommendations = listOf(
        PlaceOffer(
            id = 1,
            city = context.getString(R.string.istanbul),
            description = context.getString(R.string.popular_place),
            imageResId = R.drawable.image_4
        ),
        PlaceOffer(
            id = 2,
            city = context.getString(R.string.sochi),
            description = context.getString(R.string.popular_place),
            imageResId = R.drawable.image_1
        ),
        PlaceOffer(
            id = 3,
            city = context.getString(R.string.phuket),
            description = context.getString(R.string.popular_place),
            imageResId = R.drawable.image_5
        )
    )

    fun getDestinationRecommendations() = destinationRecommendations
}