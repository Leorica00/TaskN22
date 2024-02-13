package com.example.taskn22.data.mapper.story

import com.example.taskn22.data.model.StoryDto
import com.example.taskn22.domain.model.GetStory

fun StoryDto.toDomain() = GetStory(
    id = id,
    cover = cover,
    title = title
)