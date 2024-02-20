package com.example.taskn22.presentation.screen.post_details

import com.example.taskn22.data.repository.post.FakePostsRepositoryImpl
import com.example.taskn22.domain.usecase.feed.GetPostByIdUseCase
import com.example.taskn22.presentation.event.PostDetailsEvent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach

@OptIn(ExperimentalCoroutinesApi::class)
class PostDetailsViewModelTest {
    private lateinit var viewModel: PostDetailsViewModel
    private lateinit var getPostUseCase: GetPostByIdUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val fakePostsRepository = FakePostsRepositoryImpl()

        getPostUseCase = GetPostByIdUseCase(fakePostsRepository)

        viewModel = PostDetailsViewModel(getPostUseCase)
    }

    @Test
    fun `try to fetch data on id that not exist gives me error`() {
        viewModel.onEvent(PostDetailsEvent.GetPostEvent(5))
        assertThat(viewModel.postDetailsStateFlow.value.errorMessage).isNotNull()
    }

    @Test
    fun `try to fetch data on id that exist and get data accordingly`() {
        viewModel.onEvent(PostDetailsEvent.GetPostEvent(2))
        assertThat(viewModel.postDetailsStateFlow.value.post).isNotNull()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }
}