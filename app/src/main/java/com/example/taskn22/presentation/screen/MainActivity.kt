package com.example.taskn22.presentation.screen

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.taskn22.R
import com.example.taskn22.databinding.ActivityMainBinding
import com.example.taskn22.presentation.screen.posts.PostsFragmentDirections
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readPushToken()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        getToDetailsFragment()

        binding.bottomNavigation.setupWithNavController(navController)

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

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    private fun readPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            d("FirebaseToken", token)

        })
    }

    private fun getToDetailsFragment() {
        val id = intent.getStringExtra("id")
        id?.let {
            val navDirections =
                PostsFragmentDirections.actionGlobalPostDetailsFragment(it.toIntOrNull() ?: 0)
            navController.navigate(navDirections)
        }
    }
}