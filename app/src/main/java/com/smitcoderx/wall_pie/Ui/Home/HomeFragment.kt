package com.smitcoderx.wall_pie.Ui.Home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.smitcoderx.wall_pie.Adapters.HomeAdapter
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.Paging.PagingLoadStateAdapter
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.Ui.UnsplashViewModel
import com.smitcoderx.wall_pie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    HomeAdapter.OnItemClickListener {

    private val viewModel by viewModels<UnsplashViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        viewModel.searchPhotos("Wallpaper")

        val adapter = HomeAdapter(this)
        binding.apply {
            tvDate.text = dateSetter()
            rvHome.setHasFixedSize(true)
            rvHome.itemAnimator = null
            rvHome.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter { adapter.retry() },
                footer = PagingLoadStateAdapter { adapter.retry() }
            )

            btnRetry.setOnClickListener {
                adapter.retry()
            }

        }
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                pbPaging.isVisible = loadState.source.refresh is LoadState.Loading
                rvHome.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // Empty View
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvHome.isVisible = false
                    tvNotFound.isVisible = true
                } else {
                    tvNotFound.isVisible = false
                }
            }
        }
    }

    override fun onClick(photo: UnsplashPhoto) {
        val action = HomeFragmentDirections.actionHomeFragmentToSingleFragment(photo)
        findNavController().navigate(action)
    }

    private fun dateSetter(): String {
        val firstDate = LocalDate.now().toString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(firstDate)
        return DateTimeFormatter.ofPattern("dd MMM").format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}