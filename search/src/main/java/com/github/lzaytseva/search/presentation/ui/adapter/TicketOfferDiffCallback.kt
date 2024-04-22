package com.github.lzaytseva.search.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.TicketOffer

internal object TicketOfferDiffCallback: DiffUtil.ItemCallback<TicketOffer>() {

    override fun areItemsTheSame(oldItem: TicketOffer, newItem: TicketOffer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TicketOffer, newItem: TicketOffer): Boolean {
        return oldItem == newItem
    }
}