package com.smitcoderx.wall_pie.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.databinding.ItemHomeLayoutBinding

class SavedAdapter(private val listener: onItemClick) :
    RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    inner class SavedViewHolder(private val binding: ItemHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = differ.currentList[position]
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls?.full)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_placeholder)
                    .into(ivWallImage)

                tvLikes.text = "${photo.likes} likes"
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
            oldItem.prim_id == newItem.prim_id

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding =
            ItemHomeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount() = differ.currentList.size

    interface onItemClick {
        fun onClick(photo: UnsplashPhoto)
    }
}

