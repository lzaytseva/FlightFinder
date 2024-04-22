package com.github.lzaytseva.search.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.lzaytseva.search.domain.model.Ticket

internal object TicketDiffCallback : DiffUtil.ItemCallback<Ticket>() {

    override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
        return oldItem == newItem
    }
}