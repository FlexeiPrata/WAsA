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
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class AssociationsFragment :
    BaseFragment<FragmentMenuBinding, AssociationsFragment.MainStates, AssociationsViewModel>() {

    override val viewModel: AssociationsViewModel by viewModels()
    private lateinit var adapter: WordsAdapter

    sealed class MainActions : Intent {
        data class GetWordsStimulus(val word: String, val chip: String?) : MainActions()
    }

    sealed class MainStates : State {
        data class SubmitList(val list: List<Item>) : MainStates()
        object Loading : MainStates()
        object Default : MainStates()
    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater, container, false)
    }

    @OptIn(ExperimentalTime::class)
    override fun render(state: MainStates) {
        when (state) {
            MainStates.Default -> binding.progressBar.isVisible = false
            MainStates.Loading -> binding.progressBar.isVisible = true
            is MainStates.SubmitList -> {
                adapter.submitList(state.list)
                binding.recycler.viewSuspendAction(milliseconds(200)) {
                    it.smoothScrollToPosition(0)
                    binding.progressBar.isVisible = false
                }
            }
        }

    }

    private fun getChips(): List<String> {
        return mutableListOf(
            getString(R.string.noun),
            getString(R.string.adjective),
            getString(R.string.verb),
            getString(R.string.adverb)
        )
    }

    private fun convertChip(chip: String?): String? {
        return when (chip) {
            getString(R.string.noun) -> "noun"
            getString(R.string.adjective) -> "adjective"
            getString(R.string.adverb) -> "adverb"
            getString(R.string.verb) -> "verb"
            else -> null
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
                viewModel.handleAction(
                    MainActions.GetWordsStimulus(
                        textRequest,
                        convertChip(chips.getActiveChip()?.text?.toString())
                    )
                )
            }
        }
        getChips().forEach {
            val chip = Chip(requireContext())
            chip.text = it
            chip.isCheckable = true
            chip.isChecked = false
            binding.chips.addView(chip)
        }
        binding.toolbar.setClearOnClickListener {
            adapter.submitList(emptyList())
            binding.apply {
                textInput.clearFocus()
                textInput.setText("")
            }
        }
        binding.toolbar.setBackOnClickListener {
            popBack()
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