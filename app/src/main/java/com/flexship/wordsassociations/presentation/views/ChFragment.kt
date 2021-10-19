package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flexship.wordsassociations.common.Action
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.databinding.FragmentChBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChFragment: BaseFragment<FragmentChBinding, ChFragment.ChState, ChViewModel>() {

    data class ChState(override var isLoading: Boolean): State

    sealed class ChAction(): Action

    override val viewModel: ChViewModel by viewModels()

    override fun setupViews() {
        binding.buttonMain.setOnClickListener {
            findNavController().navigate(ChFragmentDirections.actionChFragmentToMenuFragment())
        }
        binding.buttonGame1.setOnClickListener {
            findNavController().navigate(ChFragmentDirections.actionChFragmentToGameFragment())
        }
    }

    override fun setupStateObserver() {

    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChBinding {
        return FragmentChBinding.inflate(inflater, container, false)
    }

    override fun render(state: ChState) {

    }
}