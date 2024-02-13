package com.example.taskn22.domain.model

data class GetPost(
    val id: Int,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val shareContent: String,
    val owner: GetOwner
) {
    data class GetOwner(
        val fullName: String,
        val profile: String?,
        val postDate: String
    )
}