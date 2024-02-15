package com.example.taskn22.presentation.state

import com.example.taskn22.presentation.model.Post

data class PostDetailsState(
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val post: Post? = null
)