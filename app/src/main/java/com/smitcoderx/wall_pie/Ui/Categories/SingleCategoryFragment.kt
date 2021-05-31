package com.smitcoderx.wall_pie.Ui.Categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.databinding.FragmentSingleCategoryBinding

class SingleCategoryFragment : Fragment(R.layout.fragment_single_category) {

    private lateinit var binding: FragmentSingleCategoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleCategoryBinding.bind(view)
    }

}