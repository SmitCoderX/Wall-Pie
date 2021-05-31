package com.smitcoderx.wall_pie.Paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smitcoderx.wall_pie.databinding.WallPieLoadStateLayoutBinding

class PagingLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: WallPieLoadStateLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvRetryError.isVisible = loadState !is LoadState.Loading
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = WallPieLoadStateLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}