package com.github.lzaytseva.search.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.lzaytseva.search.domain.model.PlaceOffer

internal object PlaceOfferDiffCallback: DiffUtil.ItemCallback<PlaceOffer>() {

    override fun areItemsTheSame(oldItem: PlaceOffer, newItem: PlaceOffer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaceOffer, newItem: PlaceOffer): Boolean {
        return oldItem == newItem
    }
}