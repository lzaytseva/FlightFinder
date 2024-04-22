package com.github.lzaytseva.search.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.lzaytseva.search.databinding.ItemFlightOfferBinding
import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.uikit.R

internal class TicketOfferAdapter :
    ListAdapter<TicketOffer, TicketOfferAdapter.TicketOfferViewHolder>(TicketOfferDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketOfferViewHolder {
        val binding = ItemFlightOfferBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketOfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketOfferViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    internal class TicketOfferViewHolder(private val binding: ItemFlightOfferBinding) :
        ViewHolder(binding.root) {
        fun bind(offer: TicketOffer) {
            binding.tvAirline.text = offer.title
            binding.tvTimeRange.text = offer.timeRange
            binding.tvPrice.text = itemView.resources.getString(R.string.ticket_price, offer.price)
            val iconResId = when (offer.id) {
                1 -> R.drawable.ic_circle_red
                10 -> R.drawable.ic_circle_blue
                else -> R.drawable.ic_circle_white
            }
            binding.icCircle.background = ContextCompat.getDrawable(itemView.context, iconResId)
        }
    }
}