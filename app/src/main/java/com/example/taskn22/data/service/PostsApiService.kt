package com.example.taskn22.data.service

import com.example.taskn22.data.model.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostsApiService {
    @GET("25caefd7-7e7e-4178-a86f-e5cfee2d88a0")
    suspend fun getPosts(): Response<List<PostDto>>
}