package com.example.taskn22.presentation.screen.posts

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskn22.databinding.FragmentPostsBinding
import com.example.taskn22.presentation.base.BaseFragment
import com.example.taskn22.presentation.event.PostsEvent
import com.example.taskn22.presentation.extension.showSnackBar
import com.example.taskn22.presentation.model.Feed
import com.example.taskn22.presentation.screen.posts.adapter.FeedRecyclerViewAdapter
import com.example.taskn22.presentation.state.PostsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>(FragmentPostsBinding::inflate) {

    private val feedAdapter = FeedRecyclerViewAdapter()
    private val viewModel: PostsViewModel by viewModels()

    override fun setUp() {
        with(binding.recyclerViewFeed) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }
    }

    override fun setUpListeners() {
        feedAdapter.onClick = {
            viewModel.onEvent(PostsEvent.GoToDetailsFragmentEvent(it.id))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.postsStateFlow.collect {
                        handleState(it)
                    }
                }
                launch {
                   viewModel.uiEvent.collect {
                       handleUiEvent(it)
                   }
               }
            }
        }
    }

    private fun handleState(state: PostsState) = with(binding) {
        with(state) {
            posts?.let {posts->
                stories?.let {stories->
                    val feeds = listOf(Feed(1,stories, posts), Feed(2, stories, posts))
                    feedAdapter.submitList(feeds)
                }
            }

            progressBar.isVisible = isLoading

            errorMessage?.let {
                binding.root.showSnackBar(resources.getString(it))
            }
        }
    }

    private fun handleUiEvent(event: PostsViewModel.PostsFragmentUiEvent) {
        when(event) {
            is PostsViewModel.PostsFragmentUiEvent.GoToPostDetailsEvent -> findNavController().navigate(PostsFragmentDirections.actionGlobalPostDetailsFragment(event.id))
        }
    }
}