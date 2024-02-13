package com.example.taskn22.data.repository

import com.example.taskn22.data.common.HandleResponse
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.data.mapper.base.asResource
import com.example.taskn22.data.mapper.post.toDomain
import com.example.taskn22.data.service.PostsApiService
import com.example.taskn22.domain.model.GetPost
import com.example.taskn22.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(private val postsApiService: PostsApiService, private val handleResponse: HandleResponse): PostsRepository {
    override suspend fun getPosts(): Flow<Resource<List<GetPost>>> {
        return handleResponse.safeApiCall {
            postsApiService.getPosts()
        }.asResource {
            it.map { it.toDomain() }
        }
    }
}