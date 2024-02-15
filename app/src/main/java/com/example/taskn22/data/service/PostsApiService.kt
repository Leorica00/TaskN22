package com.example.taskn22.data.service

import com.example.taskn22.data.model.PostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApiService {
    @GET("25caefd7-7e7e-4178-a86f-e5cfee2d88a0")
    suspend fun getPosts(): Response<List<PostDto>>

    @GET("d02b76bb-095d-45fa-90e1-dc4733d1f247")
    suspend fun getPostById(@Query("id") id: Int): Response<PostDto>
}