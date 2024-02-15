package com.example.taskn22.presentation.event

interface PostsEvent {
    object GetPostsEvent : PostsEvent
    object GetStoriesEvent: PostsEvent
    class GoToDetailsFragmentEvent(val id: Int): PostsEvent
}