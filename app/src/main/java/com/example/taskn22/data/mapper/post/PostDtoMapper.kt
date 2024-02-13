package com.example.taskn22.data.mapper.post

import com.example.taskn22.data.model.PostDto
import com.example.taskn22.domain.model.GetPost

fun PostDto.toDomain() = GetPost(
    id = id,
    images = images,
    title = title,
    comments = comments,
    likes = likes,
    shareContent = shareContent,
    owner = owner.toDomain()
)