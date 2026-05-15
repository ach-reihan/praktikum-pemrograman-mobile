package com.openingtablexml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openingtablexml.data.ChessOpening
import com.openingtablexml.databinding.ItemFeaturedBinding

class FeaturedAdapter(
    private var featuredOpenings: List<ChessOpening>,
    private val onDetailClick: (ChessOpening) -> Unit
) : RecyclerView.Adapter<FeaturedAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFeaturedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(opening: ChessOpening) {
            binding.tvFeaturedName.text = opening.name

            Glide.with(binding.root.context)
                .load(opening.imageUrl)
                .into(binding.ivFeatured)

            binding.root.setOnClickListener { onDetailClick(opening) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeaturedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(featuredOpenings[position])
    }

    override fun getItemCount(): Int = featuredOpenings.size
}