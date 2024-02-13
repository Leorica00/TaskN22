package com.example.taskn22.presentation.screen.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskn22.databinding.ImageRecyclerItemLargeBinding
import com.example.taskn22.databinding.ImageRecyclerItemMediumBinding
import com.example.taskn22.databinding.ImageRecyclerItemSmallBinding
import com.example.taskn22.presentation.extension.loadImage
import com.example.taskn22.presentation.model.Image

class ImagesRecyclerViewAdapter: ListAdapter<Image, RecyclerView.ViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LARGE -> {
                val binding = ImageRecyclerItemLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LargeViewHolder(binding)
            }
            VIEW_TYPE_MEDIUM -> {
                val binding = ImageRecyclerItemMediumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MediumViewHolder(binding)
            }
            else -> {
                val binding = ImageRecyclerItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SmallViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LargeViewHolder -> holder.bind()
            is MediumViewHolder -> holder.bind()
            is SmallViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentListSize = currentList.size
        return when {
            currentListSize == 1 -> VIEW_TYPE_LARGE
            currentListSize == 2 -> VIEW_TYPE_MEDIUM
            currentListSize >= 3 && position == 0 -> VIEW_TYPE_MEDIUM
            else -> VIEW_TYPE_SMALL
        }
    }

    companion object {
        const val VIEW_TYPE_LARGE = 0
        const val VIEW_TYPE_MEDIUM = 1
        const val VIEW_TYPE_SMALL = 2
    }

    inner class LargeViewHolder(private val binding: ImageRecyclerItemLargeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.shapeableImageViewLarge.loadImage(currentList[adapterPosition].url)
        }
    }

    inner class MediumViewHolder(private val binding: ImageRecyclerItemMediumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.shapeableImageViewMedium.loadImage(currentList[adapterPosition].url)

        }
    }

    inner class SmallViewHolder(private val binding: ImageRecyclerItemSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.shapeableImageViewSmall.loadImage(currentList[adapterPosition].url)
        }
    }
}

class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}