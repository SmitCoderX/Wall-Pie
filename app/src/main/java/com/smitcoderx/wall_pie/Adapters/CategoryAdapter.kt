package com.smitcoderx.wall_pie.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smitcoderx.wall_pie.Models.CategoryTypes
import com.smitcoderx.wall_pie.databinding.CategoryItemLayoutBinding

class CategoryAdapter(private val listener: SetOnClick) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<CategoryTypes>() {
        override fun areItemsTheSame(oldItem: CategoryTypes, newItem: CategoryTypes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryTypes, newItem: CategoryTypes): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOnClick {
        fun onClick(types: CategoryTypes)
    }

    inner class CategoryViewHolder(private val binding: CategoryItemLayoutBinding) :
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

        fun bind(types: CategoryTypes) {
            binding.apply {
                Glide.with(itemView)
                    .load(types.bg)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivCategory)

                tvCategoryNames.text = types.type
            }
        }
    }
}