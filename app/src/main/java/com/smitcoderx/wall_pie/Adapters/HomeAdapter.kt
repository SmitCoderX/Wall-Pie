package com.smitcoderx.wall_pie.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.databinding.ItemHomeLayoutBinding

class HomeAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashPhoto, HomeAdapter.HomeFragViewHolder>(PHOTO_COMPARATOR) {

    inner class HomeFragViewHolder(private val binding: ItemHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls?.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_placeholder)
                    .into(ivWallImage)

                tvLikes.text = "${photo.likes} likes"
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(photo: UnsplashPhoto)
    }

    override fun onBindViewHolder(holder: HomeFragViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragViewHolder {
        val binding =
            ItemHomeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeFragViewHolder(binding)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }

}