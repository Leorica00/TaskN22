package com.example.taskn22.presentation.screen.posts

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskn22.R
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

        binding.btnReset.setOnClickListener {
            viewModel.onEvent(PostsEvent.GetPostsEvent)
            viewModel.onEvent(PostsEvent.GetStoriesEvent)
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
                    btnReset.visibility = View.GONE
                    val feeds = listOf(Feed(1,stories, emptyList()), Feed(2, emptyList(), posts))
                    feedAdapter.submitList(feeds)
                }
            }

            progressBar.isVisible = isLoading
            btnReset.isClickable = !isLoading
            handleButtonBackground()

            errorMessage?.let {
                binding.root.showSnackBar(resources.getString(it))
                btnReset.visibility = View.VISIBLE
            }
        }
    }

    private fun handleButtonBackground() {
        with(binding) {
            if (btnReset.isClickable) {
                btnReset.setBackgroundResource(R.drawable.btn_background)
            } else {
                btnReset.setBackgroundResource(R.drawable.btn_inactive_background)
            }
        }
    }

    private fun handleUiEvent(event: PostsViewModel.PostsFragmentUiEvent) {
        when(event) {
            is PostsViewModel.PostsFragmentUiEvent.GoToPostDetailsEvent -> findNavController().navigate(PostsFragmentDirections.actionGlobalPostDetailsFragment(event.id))
        }
    }
}