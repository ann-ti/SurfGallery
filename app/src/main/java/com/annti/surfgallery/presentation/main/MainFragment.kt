package com.annti.surfgallery.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.Navigation
import com.annti.surfgallery.R
import com.annti.surfgallery.databinding.FragmentMainBinding
import com.annti.surfgallery.domain.AuthUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainFragment : Fragment(R.layout.fragment_main) {

    private val authSessionUseCase by inject<AuthUseCase>()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            whenStarted {
                delay(800)
                val animation: Animation =
                    AnimationUtils.loadAnimation(context, R.anim.animation)
                binding.imgSplash.animation = animation
            }
            whenStarted {
                navigate()
            }
        }
    }

    private fun navigate() {
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.activityNavHost
        )
        val mainGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)

        mainGraph.setStartDestination(
            when (authSessionUseCase.isAuthorized) {
                true -> {
                    R.id.navigatorFragment
                }
                false -> {
                    R.id.loginFragment
                }
            }
        )
        navController.graph = mainGraph
    }

}