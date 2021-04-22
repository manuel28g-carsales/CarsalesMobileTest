package com.manuel28g.carsales.covidworlddata.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.manuel28g.carsales.covidworlddata.R
import com.manuel28g.carsales.covidworlddata.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

class SplashFragment: Fragment() {

    private lateinit var binding : FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        GlobalScope.launch{
            delay(1000)
            goToLoading()
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }


    private fun goToLoading(){
        findNavController().navigate(R.id.action_splash_to_home)
    }
}