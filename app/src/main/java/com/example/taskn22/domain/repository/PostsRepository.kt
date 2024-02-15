package com.example.taskn22.domain.repository

import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.domain.model.GetPost
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    suspend fun getPosts(): Flow<Resource<List<GetPost>>>
    suspend fun getPostById(id:Int): Flow<Resource<GetPost>>
}