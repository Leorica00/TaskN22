package com.example.taskn22.domain.usecase

import android.util.Log.d
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.domain.model.GetPost
import com.example.taskn22.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(): Flow<Resource<List<GetPost>>> {
        postsRepository.getPosts().collect {
            d("showFlow", it.toString())
        }
        return postsRepository.getPosts()
    }
}