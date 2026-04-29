package com.openingtablexml.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.openingtablexml.R
import com.openingtablexml.data.ChessOpening
import com.openingtablexml.data.OpeningRepository
import com.openingtablexml.databinding.FragmentListBinding
import com.openingtablexml.ui.adapter.FeaturedAdapter
import com.openingtablexml.ui.adapter.OpeningAdapter
import androidx.core.net.toUri

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allOpenings = OpeningRepository.getOpenings()
        val featuredOpenings = allOpenings.filter { it.isFeatured }

        val featuredAdapter = FeaturedAdapter(featuredOpenings) { opening ->
            navigateToDetail(opening)
        }
        binding.rvFeatured.adapter = featuredAdapter

        val listAdapter = OpeningAdapter(
            openings = allOpenings,
            onWebClick = { url ->
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                startActivity(intent)
            },
            onDetailClick = { opening ->
                navigateToDetail(opening)
            }
        )
        binding.rvOpenings.adapter = listAdapter
    }

    private fun navigateToDetail(opening: ChessOpening) {
        val bundle = Bundle().apply {
            putParcelable("opening", opening)
        }
        findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}