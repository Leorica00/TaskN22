package com.example.taskn22.di

import com.example.taskn22.data.common.HandleResponse
import com.example.taskn22.data.repository.PostsRepositoryImpl
import com.example.taskn22.data.repository.StoriesRepositoryImpl
import com.example.taskn22.data.service.PostsApiService
import com.example.taskn22.data.service.StoriesApiService
import com.example.taskn22.domain.repository.PostsRepository
import com.example.taskn22.domain.repository.StoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostsRepository(
        postsApiService: PostsApiService,
        handleResponse: HandleResponse
    ): PostsRepository =
        PostsRepositoryImpl(
            postsApiService = postsApiService,
            handleResponse = handleResponse,
        )

    @Singleton
    @Provides
    fun provideStoriesRepository(
        storiesApiService: StoriesApiService,
        handleResponse: HandleResponse
    ): StoriesRepository =
        StoriesRepositoryImpl(
            storiesApiService = storiesApiService,
            handleResponse = handleResponse,
        )
}