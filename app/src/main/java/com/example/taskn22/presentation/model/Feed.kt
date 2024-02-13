package com.example.taskn22.presentation.model

data class Feed(
    val id: Int,
    val stories: List<Story>,
    val posts: List<Post>
)
