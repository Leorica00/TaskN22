package com.example.taskn22.domain.usecase

import com.example.taskn22.domain.repository.StoriesRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val storiesRepository: StoriesRepository) {
    suspend operator fun invoke() = storiesRepository.getStories()
}