package com.github.lzaytseva.search.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.lzaytseva.search.databinding.ItemPlaceRecommendationBinding
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.uikit.R

internal class PlaceOfferAdapter(
    private val onPlaceClickListener: (String) -> Unit
) :
    ListAdapter<PlaceOffer, PlaceOfferAdapter.DestinationViewHolder>(PlaceOfferDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemPlaceRecommendationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val placeOffer = currentList[position]
        holder.bind(placeOffer)
        holder.itemView.setOnClickListener {
            onPlaceClickListener.invoke(placeOffer.city)
        }

    }

    internal class DestinationViewHolder(private val binding: ItemPlaceRecommendationBinding) :
        ViewHolder(binding.root) {
        fun bind(placeOffer: PlaceOffer) {
            binding.tvCity.text = placeOffer.city
            binding.tvDescription.text = placeOffer.description
            Glide.with(itemView)
                .load(placeOffer.imageResId)
                .transform(
                    RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.concert_poster_corners_radius))
                )
                .into(binding.ivPlace)
        }
    }
}