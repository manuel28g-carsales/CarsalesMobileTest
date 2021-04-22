package com.manuel28g.carsales.covidworlddata.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.manuel28g.carsales.covidworlddata.R
import com.manuel28g.carsales.covidworlddata.databinding.FragmentLoadingBinding
import com.manuel28g.carsales.covidworlddata.viewmodel.CovidInfoViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingFragment:Fragment() {

    private lateinit var binding : FragmentLoadingBinding
    private lateinit var viewModel: CovidInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loading, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(CovidInfoViewModel::class.java)
        viewModel.getActualDate()
        viewModel.isApiResponse().observe(viewLifecycleOwner, Observer {
            if(it){
                goToHome()
            }
        })
        return binding.root
    }


    private fun goToHome(){
        findNavController().navigate(R.id.action_loading_to_home)
    }
}