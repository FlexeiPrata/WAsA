package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexship.wordsassociations.common.Action
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.Item
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.databinding.FragmentMenuBinding
import com.flexship.wordsassociations.presentation.adapters.WordsAdapter
import com.flexship.wordsassociations.presentation.uimodels.WordUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuFragment.MainStates>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: WordsAdapter

    sealed class MainActions : Action {
        data class GetWordsStimulus(val word: String) : MainActions()
    }

    sealed class MainStates : State {
        object Loading : MainStates()
        object Static: MainStates()
        data class SetupWords(val words: List<Item>) : MainStates()
        data class Error(val message: String) : MainStates()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }


    override fun render(state: MainStates) {
        when (state) {
            is MainStates.Error -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT)
                .show()
            MainStates.Loading -> {
                binding.progressBar.isVisible = true
            }
            is MainStates.SetupWords -> {
                adapter.submitList(state.words)
                render(MainStates.Static)
            }
            MainStates.Static -> {
                binding.progressBar.isVisible = false
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
        adapter = WordsAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        binding.getResponse.setOnClickListener {
            val textRequest = binding.textInput.text.toString()
            viewModel.handleAction(MainActions.GetWordsStimulus(textRequest))
        }
    }
}