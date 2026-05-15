package com.openingtablexml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.openingtablexml.data.ChessOpening
import com.openingtablexml.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val opening = arguments?.getParcelable<ChessOpening>("opening")

        opening?.let {
            binding.tvDetailName.text = it.name
            binding.tvDetailEco.text = it.ecoCode
            binding.tvDetailDesc.text = it.description

            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}