package com.smitcoderx.wall_pie.Ui.Search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.smitcoderx.wall_pie.Adapters.HomeAdapter
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.Paging.PagingLoadStateAdapter
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.Ui.UnsplashViewModel
import com.smitcoderx.wall_pie.Util.Constants.SEARCH_IMAGE_DELAY
import com.smitcoderx.wall_pie.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), HomeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<UnsplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        val adapter = HomeAdapter(this)
        var job: Job? = null
        binding.searchBar.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_IMAGE_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchPhotos(editable.toString())
                    }
                }
            }
        }

        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
            rvSearch.apply {
                setHasFixedSize(true)
                itemAnimator = null
                rvSearch.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter { adapter.retry() },
                    footer = PagingLoadStateAdapter { adapter.retry() }
                )

                searchRetry.setOnClickListener {
                    adapter.retry()
                }
            }
        }

        viewModel.photos.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                // Error View
                pbSearch.isVisible = loadState.source.refresh is LoadState.Loading
                rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
                searchRetry.isVisible = loadState.source.refresh is LoadState.Error
                searchError.isVisible = loadState.source.refresh is LoadState.Error

                // Empty View
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvSearch.isVisible = false
                    searchNotFound.isVisible = true
                } else {
                    searchNotFound.isVisible = false
                }
            }
        }
    }

    override fun onClick(photo: UnsplashPhoto) {
        val action = SearchFragmentDirections.actionSearchFragmentToSingleFragment(photo)
        findNavController().navigate(action)
    }

}