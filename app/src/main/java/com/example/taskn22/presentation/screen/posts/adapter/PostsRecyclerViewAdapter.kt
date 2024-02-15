package com.example.taskn22.presentation.screen.posts.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskn22.databinding.PostRecyclerItemBinding
import com.example.taskn22.presentation.extension.loadImage
import com.example.taskn22.presentation.model.Image
import com.example.taskn22.presentation.model.Post
import com.example.taskn22.presentation.util.CustomLayoutManager

class PostsRecyclerViewAdapter(private val onClick: ((Post) -> Unit)?): ListAdapter<Post, PostsRecyclerViewAdapter.PostsViewHolder>(PostsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(PostRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind()
    }

    inner class PostsViewHolder(private val binding: PostRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val post = currentList[adapterPosition]
            with(binding) {
                post.owner.profile?.let {
                    shapeableImageViewProfile.loadImage(it)
                }

                tvFullName.text = post.owner.fullName
                tvPostDate.text = post.owner.postDate
                tvShareContent.text = post.title

                recyclerViewImages.adapter = ImagesRecyclerViewAdapter().apply {
                    post.images?.let {
                        submitList(it.map { Image(post.id, it) })
                    }
                }
                recyclerViewImages.layoutManager = CustomLayoutManager(2, RecyclerView.VERTICAL)

                tvMessages.text = "${post.comments} Comments"
                tvLikes.text = "${post.likes} Likes"

                binding.root.setOnClickListener {
                    onClick?.invoke(post)
                }
            }
        }
    }

    companion object {
        private val PostsDiffCallback = object : DiffUtil.ItemCallback<Post>() {

            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
}