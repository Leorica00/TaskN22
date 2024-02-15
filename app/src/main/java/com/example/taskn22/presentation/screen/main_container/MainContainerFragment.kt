package com.example.taskn22.presentation.screen.main_container

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.taskn22.R
import com.example.taskn22.databinding.FragmentMainContainerBinding
import com.example.taskn22.presentation.base.BaseFragment
import com.google.android.material.shape.ShapeAppearanceModel

class MainContainerFragment : BaseFragment<FragmentMainContainerBinding>(FragmentMainContainerBinding::inflate) {
    override fun setUp() {
        val navHostFragment = parentFragmentManager.findFragmentById(R.id.inner_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.itemActiveIndicatorShapeAppearance = ShapeAppearanceModel().withCornerSize(50f)


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favouritesPage -> {
                    true
                }
                R.id.homePage -> {
                    true
                }
                R.id.messagesPage -> {
                    true
                }
                R.id.notificationPage -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }

}