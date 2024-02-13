package com.example.taskn22.data.repository

import com.example.taskn22.data.common.HandleResponse
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.data.mapper.base.asResource
import com.example.taskn22.data.mapper.story.toDomain
import com.example.taskn22.data.service.StoriesApiService
import com.example.taskn22.domain.model.GetStory
import com.example.taskn22.domain.repository.StoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val storiesApiService: StoriesApiService,
    private val handleResponse: HandleResponse
) :
    StoriesRepository {
    override suspend fun getStories(): Flow<Resource<List<GetStory>>> {
        return handleResponse.safeApiCall {
            storiesApiService.getStories()
        }.asResource {
            it.map { it.toDomain() }
        }
    }
}