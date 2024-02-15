package com.example.taskn22.presentation.mapper

import com.example.taskn22.domain.model.GetPost
import com.example.taskn22.presentation.model.Post
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
    postDate = convertEpochToFormattedDate(postDate)
)

fun convertEpochToFormattedDate(epoch: Long): String {
    val dateFormat = SimpleDateFormat("dd MMMM 'at' h:mm a", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epoch * 1000
    return dateFormat.format(calendar.time)
}