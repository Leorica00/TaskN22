package com.example.taskn22.data.mapper.post

import com.example.taskn22.data.model.PostDto
import com.example.taskn22.domain.model.GetPost
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun PostDto.OwnerDto.toDomain() = GetPost.GetOwner(
    fullName = "$firstName $lastName",
    profile = profile,
    postDate = convertEpochToFormattedDate(postDate)
)

fun convertEpochToFormattedDate(epoch: Long): String {
    val dateFormat = SimpleDateFormat("dd MMMM 'at' h:mm a", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epoch * 1000
    return dateFormat.format(calendar.time)
}