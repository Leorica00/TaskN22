package com.example.taskn22.presentation.screen.posts

import com.example.taskn22.data.repository.post.FakePostsRepositoryImpl
import com.example.taskn22.data.repository.story.FakeStoriesRepositoryImpl
import com.example.taskn22.domain.repository.post.PostsRepository
import com.example.taskn22.domain.repository.story.StoriesRepository
import com.example.taskn22.domain.usecase.feed.GetPostsUseCase
import com.example.taskn22.domain.usecase.feed.GetStoriesUseCase
import com.example.taskn22.presentation.event.PostsEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import com.google.common.truth.Truth.assertThat
import org.junit.After

import org.junit.Before
import org.junit.Test

@kotlinx.coroutines.ExperimentalCoroutinesApi
class PostsViewModelTest {
    private lateinit var viewModel: PostsViewModel
    private lateinit var fakePostsRepository: PostsRepository
    private lateinit var fakeStoriesRepository: StoriesRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        fakePostsRepository = FakePostsRepositoryImpl()
        fakeStoriesRepository = FakeStoriesRepositoryImpl()

        viewModel = PostsViewModel(GetPostsUseCase(fakePostsRepository), GetStoriesUseCase(fakeStoriesRepository))
    }

    @Test
    fun `when fetching posts it returns data and sets state successfully`() {
        viewModel.onEvent(PostsEvent.GetPostsEvent)
        assertThat(viewModel.postsStateFlow.value.posts).isNotNull()
    }

    @Test
    fun `when fetching stories it returns data and sets state successfully`() {
        viewModel.onEvent(PostsEvent.GetStoriesEvent)
        assertThat(viewModel.postsStateFlow.value.stories).isNotNull()
    }

    @After
    fun tearDownMainDispatcher() {
        Dispatchers.resetMain()
    }
}