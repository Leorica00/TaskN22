package com.example.taskn22.presentation.event

sealed interface PostDetailsEvent {
    class GetPostEvent(val id: Int) : PostDetailsEvent
    object GoBackEvent : PostDetailsEvent
}