package com.example.taskn22.domain.repository.story

import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.domain.model.GetStory
import kotlinx.coroutines.flow.Flow

interface StoriesRepository {
    suspend fun getStories(): Flow<Resource<List<GetStory>>>
}