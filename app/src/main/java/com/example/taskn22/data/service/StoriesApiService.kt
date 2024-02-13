package com.example.taskn22.data.service

import com.example.taskn22.data.model.StoryDto
import retrofit2.Response
import retrofit2.http.GET

interface StoriesApiService {
    @GET("1e2c42be-fc82-4efb-9f3f-4ce4ce80743c")
    suspend fun getStories(): Response<List<StoryDto>>
}

