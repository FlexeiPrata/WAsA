package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexship.wordsassociations.R
import com.flexship.wordsassociations.common.Action
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.databinding.FragmentGameBinding
import com.flexship.wordsassociations.presentation.adapters.GuessAdapter
import com.flexship.wordsassociations.presentation.uimodels.GuessUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding, GameFragment.GameState, GameViewModel>() {

    override val viewModel: GameViewModel by viewModels()

    private val adapter = GuessAdapter(::deleteChip)

    data class GameState(
        override var isLoading: Boolean = false,
        var wordsList: List<GuessUIModel> = emptyList(),
        var guessWord: List<String> = mutableListOf()
    ) : State

    sealed class GameActions : Action {
        data class AddWord(val word: String) : GameActions()
        data class DeleteChip(val chip: String) : GameFragment.GameActions()
        object Clear : GameActions()
        object Guess : GameActions()
    }

    override fun setupViews() {
        binding.apply {
            guessLabel.isVisible = false
            guessText.isVisible = false
            buttonAdd.setOnClickListener {
                if (state.wordsList.size < 10) {
                    val text = textInput.editableText.toString()
                    if (viewModel.listOfGuess.size <= 10 && text.isNotEmpty()) {
                        viewModel.handleAction(GameActions.AddWord(text))
                        textInput.editableText.clear()
                    }
                }
            }
            guess.setOnClickListener {
                viewModel.handleAction(GameActions.Guess)
            }
            chips.layoutManager = LinearLayoutManager(requireContext())
            chips.adapter = adapter
        }
    }

    override fun setupStateObserver() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    render(it)
                }
            }
        }
        state = GameState()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding {
        return FragmentGameBinding.inflate(inflater, container, false)
    }

    override fun suspendLoading() {
        binding.loadingBar.isVisible = false
    }

    override fun render(state: GameState) {
        binding.apply {
            loadingBar.isVisible = state.isLoading

            binding.headerText.text = String.format(getString(R.string.guess_text, state.wordsList.size.toString()))

            adapter.submitList(state.wordsList)

            guessLabel.isVisible = !state.guessWord.isNullOrEmpty()
            guessText.isVisible = !state.guessWord.isNullOrEmpty()
            guessLabel2.isVisible = state.guessWord.size > 1
            guessTextMinor.isVisible = state.guessWord.size > 1
            if (state.guessWord.isNotEmpty())
                guessText.text = state.guessWord.first()
            val stringMinor = StringBuilder()
            if (state.guessWord.size > 1) {
                state.guessWord.forEachIndexed { index, s ->
                    if (index > 0) stringMinor.append(s)
                    if (index < state.guessWord.size - 1 && index > 0) stringMinor.append(", ")
                }
                guessTextMinor.text = stringMinor
            }
        }
    }

    private fun deleteChip(chip: String){
        viewModel.handleAction(GameActions.DeleteChip(chip))
    }
}