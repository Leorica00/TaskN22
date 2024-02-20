package com.example.taskn22.domain.usecase.feed

import com.example.taskn22.domain.repository.story.StoriesRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val storiesRepository: StoriesRepository) {
    suspend operator fun invoke() = storiesRepository.getStories()
}