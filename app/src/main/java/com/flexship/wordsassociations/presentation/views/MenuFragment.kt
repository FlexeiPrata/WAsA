package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexship.wordsassociations.common.*
import com.flexship.wordsassociations.databinding.FragmentMenuBinding
import com.flexship.wordsassociations.presentation.adapters.WordsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuFragment.MainStates>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: WordsAdapter

    sealed class MainActions : Action {
        data class GetWordsStimulus(val word: String) : MainActions()
    }

    data class MainStates(
        override var isLoading: Boolean = false,
        var listForSubmit: List<Item>? = null
    ) : State

    override var state = MainStates()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }


    override fun render(state: MainStates) {
        binding.progressBar.isVisible = state.isLoading
        state.listForSubmit?.let {
            adapter.submitList(it)
            binding.recycler.smoothScrollToPosition(0)
        }
    }

    override fun setupViews() {
        adapter = WordsAdapter()
        binding.apply {
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = adapter
            getResponse.setOnClickListener {
                val textRequest = binding.textInput.text.toString()
                hideKeyboardFrom(binding.root)
                viewModel.handleAction(MainActions.GetWordsStimulus(textRequest))
            }
        }
    }

    override fun setupStateObserver() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    render(it)
                }
            }
        }
    }
}