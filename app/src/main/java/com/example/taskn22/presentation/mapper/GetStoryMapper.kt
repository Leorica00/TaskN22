package com.example.taskn22.presentation.mapper

import com.example.taskn22.domain.model.GetStory
import com.example.taskn22.presentation.model.Story

fun GetStory.toPresentation() = Story(
    id = id,
    cover = cover,
    title = title
)