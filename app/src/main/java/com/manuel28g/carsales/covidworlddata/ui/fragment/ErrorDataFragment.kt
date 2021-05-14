package com.manuel28g.carsales.covidworlddata.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.manuel28g.carsales.covidworlddata.R
import com.manuel28g.carsales.covidworlddata.databinding.FragmentErrorDataBinding
import com.manuel28g.carsales.covidworlddata.viewmodel.CovidInfoViewModel

class ErrorDataFragment: Fragment(), RetryActionButton{

    private lateinit var mBinding: FragmentErrorDataBinding
    private lateinit var viewModel: CovidInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_error_data, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.actionButton = this

        viewModel = ViewModelProviders.of(requireActivity()).get(CovidInfoViewModel::class.java)

        return mBinding.root
    }

    override fun onClickButton() {
        viewModel.resetError()
        Navigation.findNavController(mBinding.root).popBackStack()
    }


}


