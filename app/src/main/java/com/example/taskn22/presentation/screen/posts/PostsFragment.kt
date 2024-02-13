package com.example.taskn22.presentation.screen.posts

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskn22.databinding.FragmentPostsBinding
import com.example.taskn22.presentation.base.BaseFragment
import com.example.taskn22.presentation.screen.posts.adapter.PostsRecyclerViewAdapter
import com.example.taskn22.presentation.screen.posts.adapter.StoriesRecyclerViewAdapter
import com.example.taskn22.presentation.state.PostsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : BaseFragment<FragmentPostsBinding>(FragmentPostsBinding::inflate) {

    private val postsRecyclerAdapter = PostsRecyclerViewAdapter()
    private val storiesRecyclerAdapter = StoriesRecyclerViewAdapter()
    private val viewModel: PostsViewModel by viewModels()

    override fun setUp() {
        with(binding.recyclerViewPosts) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postsRecyclerAdapter
        }

        with(binding.recyclerViewStories) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = storiesRecyclerAdapter
        }
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: PostsState) = with(binding) {
        with(state) {
            posts?.let {
                postsRecyclerAdapter.submitList(it)
            }

            stories?.let {
                storiesRecyclerAdapter.submitList(it)
            }

            progressBar.isVisible = isLoading

            errorMessage?.let {

            }
        }
    }
}