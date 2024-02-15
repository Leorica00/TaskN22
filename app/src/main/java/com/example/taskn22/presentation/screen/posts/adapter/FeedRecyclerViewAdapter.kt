package com.example.taskn22.presentation.screen.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskn22.databinding.FeedRecyclerPostItemBinding
import com.example.taskn22.databinding.FeedRecyclerStoryItemBinding
import com.example.taskn22.presentation.model.Feed
import com.example.taskn22.presentation.model.Post

class FeedRecyclerViewAdapter : ListAdapter<Feed, RecyclerView.ViewHolder>(FeedDiffCallback) {

    var onClick: ((Post) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            POST -> FeedPostViewHolder(FeedRecyclerPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            STORY -> FeedStoryViewHolder(FeedRecyclerStoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> FeedPostViewHolder(FeedRecyclerPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FeedStoryViewHolder -> holder.bind()
            is FeedPostViewHolder -> holder.bind()
        }
    }

    inner class FeedPostViewHolder(private val binding: FeedRecyclerPostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val posts = currentList[adapterPosition].posts
            with(binding.postsRecyclerView) {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = PostsRecyclerViewAdapter(onClick).apply {
                    this.submitList(posts)
                }
            }
        }
    }

    inner class FeedStoryViewHolder(private val binding: FeedRecyclerStoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val stories = currentList[adapterPosition].stories
            with(binding.root) {
                layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
                adapter = StoriesRecyclerViewAdapter().apply {
                    this.submitList(stories)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> STORY
            1 -> POST
            else -> POST
        }
    }

    companion object {
        const val STORY = 1
        const val POST = 2

        private val FeedDiffCallback = object : DiffUtil.ItemCallback<Feed>() {

            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
                return oldItem == newItem
            }
        }
    }
}