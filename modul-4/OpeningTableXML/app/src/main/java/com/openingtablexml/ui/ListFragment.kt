package com.openingtablexml.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.openingtablexml.R
import com.openingtablexml.data.ChessOpening
import com.openingtablexml.databinding.FragmentListBinding
import com.openingtablexml.ui.adapter.FeaturedAdapter
import com.openingtablexml.ui.adapter.OpeningAdapter
import com.openingtablexml.ui.viewmodel.OpeningEvent
import com.openingtablexml.ui.viewmodel.OpeningViewModel
import com.openingtablexml.ui.viewmodel.OpeningViewModelFactory
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OpeningViewModel by viewModels {
        OpeningViewModelFactory("ListFragmentLog")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.openings.collect { openingsList ->
                        if (openingsList.isNotEmpty()) {
                            setupRecyclerView(openingsList)
                        }
                    }
                }

                launch {
                    viewModel.navigationEvent.collect { event ->
                        when (event) {
                            is OpeningEvent.NavigateToDetail -> {
                                navigateToDetail(event.opening)
                                viewModel.onEventHandled()
                            }
                            is OpeningEvent.NavigateToWeb -> {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
                                startActivity(intent)
                                viewModel.onEventHandled()
                            }
                            null -> {
                            }
                        }
                    }
                }

            }
        }
    }

    private fun setupRecyclerView(allOpenings: List<ChessOpening>) {
        val featuredOpenings = allOpenings.filter { it.isFeatured }

        val featuredAdapter = FeaturedAdapter(featuredOpenings) { opening ->
            viewModel.onDetailClicked(opening)
        }
        binding.rvFeatured.adapter = featuredAdapter

        val listAdapter = OpeningAdapter(
            openings = allOpenings,
            onWebClick = { url ->
                viewModel.onWebClicked(url)
            },
            onDetailClick = { opening ->
                viewModel.onDetailClicked(opening)
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