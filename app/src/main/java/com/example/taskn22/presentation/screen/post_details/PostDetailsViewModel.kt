package com.example.taskn22.presentation.screen.post_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn21.data.remote.common.Resource
import com.example.taskn22.domain.usecase.feed.GetPostByIdUseCase
import com.example.taskn22.presentation.event.PostDetailsEvent
import com.example.taskn22.presentation.mapper.toPresentation
import com.example.taskn22.presentation.state.PostDetailsState
import com.example.taskn22.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(private val getPostByIdUseCase: GetPostByIdUseCase) :
    ViewModel() {

    private val _postDetailsStateFlow = MutableStateFlow(PostDetailsState())
    val postDetailsStateFlow = _postDetailsStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PostDetailsUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: PostDetailsEvent) {
        when (event) {
            is PostDetailsEvent.GetPostEvent -> getPostById(id = event.id)
            is PostDetailsEvent.GoBackEvent -> goBack()
        }
    }

    private fun getPostById(id: Int) {
        viewModelScope.launch {
            getPostByIdUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> _postDetailsStateFlow.update { currentState ->
                        currentState.copy(
                            post = resource.response.toPresentation()
                        )
                    }

                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Loading -> _postDetailsStateFlow.update { currentState ->
                        currentState.copy(
                            isLoading = resource.loading
                        )
                    }
                }
            }
        }
    }

    private fun goBack() {
        viewModelScope.launch {
            _uiEvent.emit(PostDetailsUIEvent.GoBackEvent)
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _postDetailsStateFlow.update { currentState ->
            currentState.copy(errorMessage = errorMessage, isLoading = false)
        }
    }

    sealed interface PostDetailsUIEvent {
        object GoBackEvent: PostDetailsUIEvent
    }

}