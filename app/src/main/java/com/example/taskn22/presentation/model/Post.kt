package com.example.taskn22.presentation.model

data class Post(
    val id: Int,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val shareContent: String,
    val owner: Owner
) {
    data class Owner(
        val fullName: String,
        val profile: String?,
        val postDate: String
    )
}