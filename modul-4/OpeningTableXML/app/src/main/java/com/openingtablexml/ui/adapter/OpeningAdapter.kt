package com.openingtablexml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openingtablexml.data.ChessOpening
import com.openingtablexml.databinding.ItemOpeningBinding

class OpeningAdapter(
    private var openings: List<ChessOpening>,
    private val onWebClick: (String) -> Unit,
    private val onDetailClick: (ChessOpening) -> Unit
) : RecyclerView.Adapter<OpeningAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemOpeningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(opening: ChessOpening) {
            binding.tvName.text = opening.name
            binding.tvEcoCode.text = opening.ecoCode
            binding.tvCategory.text = opening.category
            binding.tvMoves.text = "• ${opening.moves}"

            Glide.with(binding.root.context)
                .load(opening.imageUrl)
                .into(binding.ivOpening)

            binding.btnExternal.setOnClickListener { onWebClick(opening.externalUrl) }
            binding.btnDetail.setOnClickListener { onDetailClick(opening) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOpeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(openings[position])
    }

    override fun getItemCount(): Int = openings.size
}