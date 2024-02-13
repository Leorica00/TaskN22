package com.example.taskn22.presentation.screen.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskn22.databinding.StoryRecyclerItemBinding
import com.example.taskn22.presentation.extension.loadImage
import com.example.taskn22.presentation.model.Story

class StoriesRecyclerViewAdapter: ListAdapter<Story, StoriesRecyclerViewAdapter.StoriesViewHolder>(StoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        return StoriesViewHolder(StoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind()
    }

    inner class StoriesViewHolder(private val binding: StoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val story = currentList[adapterPosition]
            with(binding) {
                shapeableImageViewCover.loadImage(story.cover)
                tvTitle.text = story.title
            }
        }
    }

    companion object {
        private val StoryDiffCallback = object : DiffUtil.ItemCallback<Story>() {

            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}