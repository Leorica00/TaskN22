package com.example.taskn22.presentation.mapper

import com.example.taskn22.domain.model.GetPost
import com.example.taskn22.presentation.model.Post

fun GetPost.toPresentation() = Post(
    id = id,
    images = images,
    title = title,
    comments = comments,
    likes = likes,
    shareContent = shareContent,
    owner = owner.toPresentation()
)

fun GetPost.GetOwner.toPresentation() = Post.Owner(
    fullName = fullName,
    profile = profile,
    postDate = postDate
)