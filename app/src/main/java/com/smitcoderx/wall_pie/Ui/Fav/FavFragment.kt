   package com.smitcoderx.wall_pie.Ui.Fav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smitcoderx.wall_pie.Adapters.SavedAdapter
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.Ui.UnsplashViewModel
import com.smitcoderx.wall_pie.databinding.FragmentFavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment(R.layout.fragment_fav), SavedAdapter.onItemClick {

    private lateinit var binding: FragmentFavBinding
    private lateinit var homeAdapter: SavedAdapter
    private val viewModel: UnsplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavBinding.bind(view)

        homeAdapter = SavedAdapter(this)
        binding.apply {
            rvFav.setHasFixedSize(true)
            rvFav.itemAnimator = null
            rvFav.adapter = homeAdapter
        }

        viewModel.savedPhotos().observe(viewLifecycleOwner, {
            homeAdapter.differ.submitList(it)
        })
    }

    override fun onClick(photo: UnsplashPhoto) {
        val action = FavFragmentDirections.actionFavFragmentToSingleFragment(photo)
        findNavController().navigate(action)
    }
}