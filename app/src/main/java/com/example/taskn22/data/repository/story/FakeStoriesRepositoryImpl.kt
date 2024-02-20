package com.example.taskn22.data.repository.story

import com.example.taskn21.data.remote.common.HandleErrorStates
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.data.mapper.story.toDomain
import com.example.taskn22.data.model.StoryDto
import com.example.taskn22.domain.model.GetStory
import com.example.taskn22.domain.repository.story.StoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class FakeStoriesRepositoryImpl: StoriesRepository {
    private val fakeListOfStories = listOf(
        StoryDto(
            id = 1,
            cover = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTr3cIdauesbQH1_Dd2WKtkgPxK0dV2rrWU3A&usqp=CAU",
            title = "title 1"
        ),
        StoryDto(
            id = 2,
            cover = "https://cdn0.thedailyeco.com/en/posts/6/4/0/natural_regions_definition_and_examples_46_orig.jpg",
            title = "title 2"
        ),
        StoryDto(
            id = 3,
            cover = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuFLT26U49p0XhoQLNSBodEqpF2nk-Zb2zXw&usqp=CAU",
            title = "რიწის ტბა"
        )
    )

    override suspend fun getStories(): Flow<Resource<List<GetStory>>> {
        return flow {
            emit(Resource.Loading(loading = true))
            emit(Resource.Success(response = fakeListOfStories.map { it.toDomain() }))
        }.catch {
            emit(Resource.Error(HandleErrorStates.handleException(it), it))
        }.onCompletion {
            emit(Resource.Loading(loading = false))
        }
    }


}