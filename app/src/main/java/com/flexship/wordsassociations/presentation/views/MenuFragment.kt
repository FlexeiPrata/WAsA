package com.flexship.wordsassociations.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.flexship.wordsassociations.common.Action
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.data.models.Words
import com.flexship.wordsassociations.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment: BaseFragment<FragmentMenuBinding, MenuFragment.MainStates>() {

    private val viewModel: MainViewModel by viewModels()

    sealed class MainActions : Action {
        data class GetWordsStimulus(val word: String) : MainActions()
    }

    sealed class MainStates : State {
        object Loading : MainStates()
        data class SetupWords(val words: List<Words>?) : MainStates()
        data class Error(val message: String) : MainStates()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }


    override fun render(state: MainStates) {
        when (state){
            is MainStates.Error -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            MainStates.Loading -> {}
            is MainStates.SetupWords -> {
                var string = ""
                state.words?.forEach {
                    string += it.item
                    string += "\n"
                }
                requestBinding().text.text = string
            }
        }
    }

    override fun setupViews() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    render(it)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.handleAction(MainActions.GetWordsStimulus("Сердце"))
        }
    }
}