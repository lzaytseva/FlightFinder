package com.github.lzaytseva.search.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.lzaytseva.search.domain.model.ConcertOffer

internal object ConcertOfferDiffCallback: DiffUtil.ItemCallback<ConcertOffer>() {

    override fun areItemsTheSame(oldItem: ConcertOffer, newItem: ConcertOffer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ConcertOffer, newItem: ConcertOffer): Boolean {
        return oldItem == newItem
    }
}