package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.Intent
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.databinding.FragmentChBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartMenuFragment : BaseFragment<FragmentChBinding, StartMenuFragment.ChState, StartMenuViewModel>() {

    sealed class ChState : State {
        object Default: ChState()
    }

    sealed class ChAction : Intent

    override val viewModel: StartMenuViewModel by viewModels()

    override fun setupViews() {
        binding.buttonMain.setOnClickListener {
            navigate(StartMenuFragmentDirections.actionChFragmentToMenuFragment())
        }
        binding.buttonGame1.setOnClickListener {
            navigate(StartMenuFragmentDirections.actionChFragmentToGameFragment())
        }

        binding.buttonInfo.setOnClickListener {
            DialogInfo().show(requireActivity().supportFragmentManager, "info")
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