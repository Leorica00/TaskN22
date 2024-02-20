package com.example.taskn22.data.repository.post

import com.example.taskn21.data.remote.common.HandleErrorStates
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.data.mapper.post.toDomain
import com.example.taskn22.data.model.PostDto
import com.example.taskn22.domain.model.GetPost
import com.example.taskn22.domain.repository.post.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class FakePostsRepositoryImpl : PostsRepository {

    private val fakeListOfPosts = listOf(
        PostDto(
            id = 1,
            images = listOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTr3cIdauesbQH1_Dd2WKtkgPxK0dV2rrWU3A&usqp=CAU",
                "https://cdn0.thedailyeco.com/en/posts/6/4/0/natural_regions_definition_and_examples_46_orig.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuFLT26U49p0XhoQLNSBodEqpF2nk-Zb2zXw&usqp=CAU"
            ),
            title = "title 1",
            comments = 8,
            likes = 89,
            shareContent = "Share post content",
            owner = PostDto.OwnerDto(
                firstName = "Jemali",
                lastName = "Kakauridze",
                profile = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxNpXDhReGbWgHFTwkrGEdrDi4OZikaZGViWtkLHcudA&s",
                postDate = 1707732926
            )
        ),
        PostDto(
            id = 2,
            images = listOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTr3cIdauesbQH1_Dd2WKtkgPxK0dV2rrWU3A&usqp=CAU",
                "https://cdn0.thedailyeco.com/en/posts/6/4/0/natural_regions_definition_and_examples_46_orig.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTuFLT26U49p0XhoQLNSBodEqpF2nk-Zb2zXw&usqp=CAU"
            ),
            title = "title 2",
            comments = 8,
            likes = 89,
            shareContent = "Share post content",
            owner = PostDto.OwnerDto(
                firstName = "Jemali",
                lastName = "Kakauridze",
                profile = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxNpXDhReGbWgHFTwkrGEdrDi4OZikaZGViWtkLHcudA&s",
                postDate = 1707732926
            )
        )
    )
    override suspend fun getPosts(): Flow<Resource<List<GetPost>>> {
        return flow {
            emit(Resource.Loading(loading = true))
            emit(Resource.Success(response = fakeListOfPosts.map { it.toDomain() }))
        }.catch {
            emit(Resource.Error(HandleErrorStates.handleException(it), it))
        }.onCompletion {
            emit(Resource.Loading(loading = false))
        }
    }

    override suspend fun getPostById(id: Int): Flow<Resource<GetPost>> {
        return flow {
            emit(Resource.Loading(loading = true))
            emit(Resource.Success(response = (fakeListOfPosts.first { it.id == id }).toDomain()))
        }.catch {
            emit(Resource.Error(HandleErrorStates.handleException(it), it))
        }.onCompletion {
            emit(Resource.Loading(loading = false))
        }
    }
}