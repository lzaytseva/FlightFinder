package com.github.lzaytseva.search.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.lzaytseva.search.databinding.ItemConcertOfferBinding
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.uikit.R

internal class ConcertOfferAdapter :
    ListAdapter<ConcertOffer, ConcertOfferAdapter.ConcertOfferViewHolder>(ConcertOfferDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcertOfferViewHolder {
        val binding = ItemConcertOfferBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ConcertOfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConcertOfferViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    internal class ConcertOfferViewHolder(private val binding: ItemConcertOfferBinding) :
        ViewHolder(binding.root) {
        fun bind(offer: ConcertOffer) {
            binding.tvCity.text = offer.city
            binding.tvPrice.text = itemView.resources.getString(R.string.price_from, offer.price)
            binding.tvTitle.text = offer.title
            Glide.with(itemView)
                .load(offer.imageResId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(
                    RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.place_image_corners_radius))
                )
                .into(binding.ivPoster)
        }
    }
}