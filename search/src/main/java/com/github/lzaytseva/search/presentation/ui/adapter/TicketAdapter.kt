package com.github.lzaytseva.search.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lzaytseva.search.databinding.ItemTicketBinding
import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.uikit.R

internal class TicketAdapter :
    ListAdapter<Ticket, TicketAdapter.TicketViewHolder>(TicketDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    internal class TicketViewHolder(private val binding: ItemTicketBinding) :
        ViewHolder(binding.root) {
        fun bind(ticket: Ticket) {
            binding.tvBadge.isVisible = ticket.badge?.let {
                binding.tvBadge.text = it
                true
            } ?: false
            binding.tvPrice.text = itemView.resources.getString(R.string.ticket_price, ticket.price)
            binding.tvArrivalAirport.text = ticket.arrivalAirportCode
            binding.tvTimeArrival.text = ticket.arrivalTime
            binding.tvTimeDeparture.text = ticket.departureTime
            binding.tvDepartureAirport.text = ticket.departureAirportCode
            binding.tvTotalHours.text =
                itemView.resources.getString(R.string.travel_time, ticket.travelTime)
            binding.tvTransfer.isVisible = !ticket.hasTransfer
        }
    }
}