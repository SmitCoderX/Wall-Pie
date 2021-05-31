package com.smitcoderx.wall_pie.Ui.Categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.smitcoderx.wall_pie.Adapters.CategoryAdapter
import com.smitcoderx.wall_pie.Models.CategoryTypes
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category), CategoryAdapter.SetOnClick {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        categoryAdapter = CategoryAdapter(this)
        setData()
        binding.apply {
            rvCategory.setHasFixedSize(true)
            rvCategory.adapter = categoryAdapter
        }
    }


    private fun setData() {
        val types = mutableListOf(
            CategoryTypes(1, R.drawable.abs, "Abstract"),
            CategoryTypes(2, R.drawable.minimal, "Minimal"),
            CategoryTypes(3, R.drawable.logo, "Logos"),
            CategoryTypes(4, R.drawable.anime, "Anime"),
            CategoryTypes(5, R.drawable.games, "Games"),
            CategoryTypes(6, R.drawable.cars, "Cars"),
            CategoryTypes(7, R.drawable.bike, "Bikes"),
            CategoryTypes(8, R.drawable.places, "Places"),
            CategoryTypes(9, R.drawable.landscape, "Landscapes"),
            CategoryTypes(10, R.drawable.threed, "3D"),
            CategoryTypes(11, R.drawable.amoled, "Amoled"),
            CategoryTypes(12, R.drawable.cartoons, "Cartoons")
        )

        categoryAdapter.differ.submitList(types)
    }

    override fun onClick(types: CategoryTypes) {
        val action =
            CategoryFragmentDirections.actionCategoryFragmentToSingleCategoryFragment(types)
        findNavController().navigate(action)
    }
}