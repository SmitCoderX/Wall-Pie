package com.smitcoderx.wall_pie.Ui.Categories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.smitcoderx.wall_pie.Adapters.HomeAdapter
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.Paging.PagingLoadStateAdapter
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.Ui.UnsplashViewModel
import com.smitcoderx.wall_pie.databinding.FragmentSingleCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleCategoryFragment : Fragment(R.layout.fragment_single_category),
    HomeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentSingleCategoryBinding
    private val args by navArgs<SingleCategoryFragmentArgs>()
    private val viewModel by viewModels<UnsplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleCategoryBinding.bind(view)

        val adapter = HomeAdapter(this)

        binding.apply {
            val cateogry = args.categoryTypes
            viewModel.searchPhotos(cateogry.type!!.toString())
            categoryName.text = cateogry.type!!.toString()
            rvSingleCat.setHasFixedSize(true)
            rvSingleCat.itemAnimator = null
            rvSingleCat.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter { adapter.retry() },
                footer = PagingLoadStateAdapter { adapter.retry() }
            )

            btnRetryCat.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                // Error View
                pbCategory.isVisible = loadState.source.refresh is LoadState.Loading
                rvSingleCat.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetryCat.isVisible = loadState.source.refresh is LoadState.Error
                tvErrorCat.isVisible = loadState.source.refresh is LoadState.Error

                // Empty View
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvSingleCat.isVisible = false
                    tvNotFoundCat.isVisible = true
                } else {
                    tvNotFoundCat.isVisible = false
                }
            }
        }
    }

    override fun onClick(photo: UnsplashPhoto) {
        val action =
            SingleCategoryFragmentDirections.actionSingleCategoryFragmentToSingleFragment(photo)
        findNavController().navigate(action)
    }
}