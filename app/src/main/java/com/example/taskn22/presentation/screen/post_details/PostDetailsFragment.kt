package com.example.taskn22.presentation.screen.post_details

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskn22.databinding.FragmentPostDetailsBinding
import com.example.taskn22.presentation.base.BaseFragment
import com.example.taskn22.presentation.event.PostDetailsEvent
import com.example.taskn22.presentation.extension.loadImage
import com.example.taskn22.presentation.extension.showSnackBar
import com.example.taskn22.presentation.model.Image
import com.example.taskn22.presentation.screen.posts.adapter.ImagesRecyclerViewAdapter
import com.example.taskn22.presentation.state.PostDetailsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailsFragment :
    BaseFragment<FragmentPostDetailsBinding>(FragmentPostDetailsBinding::inflate) {

    private val viewModel: PostDetailsViewModel by viewModels()
    private val args: PostDetailsFragmentArgs by navArgs()
    override fun setUp() {
        viewModel.onEvent(
            PostDetailsEvent.GetPostEvent(args.id)
        )
    }

    override fun setUpListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(PostDetailsEvent.GoBackEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.postDetailsStateFlow.collect {
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

    @SuppressLint("SetTextI18n")
    private fun handleState(state: PostDetailsState) {
        with(binding) {
            state.post?.let { post ->
                with(post) {
                    tvFullName.text = owner.fullName
                    tvPostDate.text = owner.postDate
                    shapeableImageViewProfile.loadImage(owner.profile)
                    tvLikes.text = "$likes likes"
                    tvMessages.text = "$comments comments"
                    recyclerViewImages.layoutManager =
                        StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                    recyclerViewImages.adapter = ImagesRecyclerViewAdapter().apply {
                        submitList(images?.map { Image(id, it) })
                    }

                }
            }
            progressBar.isVisible = state.isLoading

            state.errorMessage?.let {
                binding.root.showSnackBar(resources.getString(it))
            }
        }
    }

    private fun handleUiEvent(event: PostDetailsViewModel.PostDetailsUIEvent) {
        when(event) {
            is PostDetailsViewModel.PostDetailsUIEvent.GoBackEvent -> findNavController().navigateUp()
        }
    }
}