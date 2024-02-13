package com.example.taskn22.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.taskn22.R
import com.example.taskn22.databinding.ActivityMainBinding
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
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
}