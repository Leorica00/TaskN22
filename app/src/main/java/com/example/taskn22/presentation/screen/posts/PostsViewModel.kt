package com.example.taskn22.presentation.screen.posts

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.domain.usecase.feed.GetPostsUseCase
import com.example.taskn22.domain.usecase.feed.GetStoriesUseCase
import com.example.taskn22.presentation.event.PostsEvent
import com.example.taskn22.presentation.util.getErrorMessage
import com.example.taskn22.presentation.mapper.toPresentation
import com.example.taskn22.presentation.state.PostsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getStoriesUseCase: GetStoriesUseCase,
) : ViewModel() {

    private val _postsStateFlow = MutableStateFlow(PostsState())
    val postsStateFlow = _postsStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PostsFragmentUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    fun onEvent(event: PostsEvent) {
        when(event) {
            is PostsEvent.GetPostsEvent -> getPosts()
            is PostsEvent.GetStoriesEvent -> getStories()
            is PostsEvent.GoToDetailsFragmentEvent -> goToDetails(event.id)
        }
    }

    init {
        getPosts()
        getStories()
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().collect { resource ->
                d("showResource", resource.toString())
                when (resource) {
                    is Resource.Loading -> {
                        _postsStateFlow.update { currentState ->
                            currentState.copy(
                                isLoading = resource.loading
                            )
                        }
                        updateErrorMessage(null)
                    }

                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Success -> _postsStateFlow.update { currentState ->
                        currentState.copy(
                            posts = resource.response.map { it.toPresentation() })
                    }
                }
            }
        }
    }

    private fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _postsStateFlow.update { currentState ->
                            currentState.copy(
                                isLoading = resource.loading
                            )
                        }
                        updateErrorMessage(null)
                    }

                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Success -> _postsStateFlow.update { currentState ->
                        currentState.copy(
                            stories = resource.response.map { it.toPresentation() })
                    }
                }
            }
        }
    }

    private fun goToDetails(id: Int) {
        viewModelScope.launch {
            _uiEvent.emit(PostsFragmentUiEvent.GoToPostDetailsEvent(id))
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _postsStateFlow.update { currentState ->
            currentState.copy(errorMessage = errorMessage, isLoading = false)
        }
    }

    sealed interface PostsFragmentUiEvent {
        class GoToPostDetailsEvent(val id: Int): PostsFragmentUiEvent
    }
}