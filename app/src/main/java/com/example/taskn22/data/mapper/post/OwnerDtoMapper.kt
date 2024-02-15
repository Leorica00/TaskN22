package com.example.taskn22.data.mapper.post

import com.example.taskn22.data.model.PostDto
import com.example.taskn22.domain.model.GetPost

fun PostDto.OwnerDto.toDomain() = GetPost.GetOwner(
    fullName = "$firstName $lastName",
    profile = profile,
    postDate = postDate
)