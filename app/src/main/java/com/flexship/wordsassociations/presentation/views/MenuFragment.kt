package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexship.wordsassociations.R
import com.flexship.wordsassociations.common.*
import com.flexship.wordsassociations.databinding.FragmentMenuBinding
import com.flexship.wordsassociations.presentation.adapters.WordsAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuFragment.MainStates, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: WordsAdapter

    sealed class MainActions : Action {
        data class GetWordsStimulus(val word: String) : MainActions()
        data class AddChip(val chip: String) : MainActions()
        data class RemoveChip(val chip: String) : MainActions()
    }

    data class MainStates(
        override var isLoading: Boolean = false,
        var listForSubmit: List<Item>? = null
    ) : State


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

    private fun getChips(): List<String> {
        return mutableListOf<String>(
            getString(R.string.noun),
            getString(R.string.adjective),
            getString(R.string.verb),
            getString(R.string.adverb)
        )
    }

    private fun convertChip(chip: String): String {
        return when (chip) {
            getString(R.string.noun) -> "noun"
            getString(R.string.adjective) -> "adjective"
            getString(R.string.adverb) -> "adverb"
            getString(R.string.verb) -> "verb"
            else -> ""
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
                textInput.clearFocus()
                textField.clearFocus()
                //Поменять
                viewModel.handleAction(MainActions.GetWordsStimulus(textRequest))
            }
        }
        getChips().forEach {

            val chip = Chip(requireContext())
            chip.text = it
            chip.isCheckable = true
            chip.isChecked = true
            chip.setOnClickListener { thisChip ->
                if (thisChip is Chip) {
                    val chipText = convertChip(thisChip.text.toString())
                    if (thisChip.isChecked) viewModel.handleAction(MainActions.AddChip(chipText))
                    else viewModel.handleAction(MainActions.RemoveChip(chipText))
                }
            }
            binding.chips.addView(chip)
            viewModel.handleAction(MainActions.AddChip(convertChip(it)))
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

    override fun suspendLoading() {
        binding.progressBar.isVisible = false
    }



}