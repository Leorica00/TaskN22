package com.example.taskn22.presentation.state

import com.example.taskn22.presentation.model.Post
import com.example.taskn22.presentation.model.Story

data class PostsState(
    val isLoading: Boolean = false,
    val posts: List<Post>? = null,
    val stories: List<Story>? = null,
    val errorMessage: Int? = null
)
