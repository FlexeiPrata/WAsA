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
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.Intent
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.common.hideKeyboardFrom
import com.flexship.wordsassociations.databinding.FragmentGameBinding
import com.flexship.wordsassociations.presentation.adapters.GuessAdapter
import com.flexship.wordsassociations.presentation.uimodels.GuessUIModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding, GameFragment.GameState, GameViewModel>() {

    override val viewModel: GameViewModel by viewModels()

    private val adapter = GuessAdapter(::deleteChip)

    sealed class GameState : State {
        data class SubmitWords(var wordsList: List<GuessUIModel> = emptyList()) : GameState()
        data class SubmitHunch(var guessWord: List<String> = mutableListOf()) : GameState()
        object Loading : GameState()
        object Default : GameState()
        object Cleared: GameState()
    }

    sealed class GameActions : Intent {
        data class AddWord(val word: String) : GameActions()
        data class DeleteChip(val chip: String) : GameFragment.GameActions()
        object Clear : GameActions()
        object Guess : GameActions()
    }

    override fun setupViews() {
        binding.apply {
            guessLabel.isVisible = false
            guessText.isVisible = false
            guessLabel2.isVisible = false
            guessTextMinor.isVisible = false
            dividerGuess.isVisible = false
            buttonAdd.setOnClickListener {
                if (adapter.itemCount < 10) {
                    val text = textInput.editableText.toString()
                    if (viewModel.listOfGuess.size <= 10 && text.isNotEmpty()) {
                        viewModel.handleAction(GameActions.AddWord(text))
                        textInput.editableText.clear()
                    }
                }
            }
            guess.setOnClickListener {
                viewModel.handleAction(GameActions.Guess)
                hideKeyboardFrom(it)
            }
            binding.toolbar.setBackOnClickListener {
                popBack()
            }
            binding.toolbar.setClearOnClickListener {
                viewModel.handleAction(GameActions.Clear)
            }
            chips.layoutManager = LinearLayoutManager(requireContext())
            chips.adapter = adapter
            headerText.text =
                String.format(getString(R.string.guess_text, "0"))
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
        when (state) {
            is GameState.SubmitHunch -> submitHunch(state.guessWord)
            is GameState.SubmitWords -> {
                binding.headerText.text =
                    String.format(getString(R.string.guess_text, state.wordsList.size.toString()))
                adapter.submitList(state.wordsList)
            }
            GameState.Default -> binding.loadingBar.isVisible = false
            GameState.Loading -> binding.loadingBar.isVisible = true
            GameState.Cleared -> {
                binding.apply {
                    guessLabel.isVisible = false
                    guessText.isVisible = false
                    guessLabel2.isVisible = false
                    guessTextMinor.isVisible = false
                    dividerGuess.isVisible = false
                    textField.clearFocus()
                    headerText.text =
                        String.format(getString(R.string.guess_text, "0"))
                    hideKeyboardFrom(root)
                }
                adapter.submitList(emptyList())

            }
        }
    }

    private fun submitHunch(guessWord: List<String>) {
        binding.apply {
            guessLabel.isVisible = guessWord.isNotEmpty()
            guessText.isVisible = guessWord.isNotEmpty()
            guessLabel2.isVisible = guessWord.size > 1
            guessTextMinor.isVisible = guessWord.size > 1
            dividerGuess.isVisible = guessWord.size > 1
            if (guessWord.isNotEmpty()) {
                guessText.text = guessWord.first()
                val stringMinor = guessWord.reduce { acc, string ->
                    "$acc, $string"
                }
                guessTextMinor.text = stringMinor
            }
            loadingBar.isVisible = false
        }
    }

    private fun deleteChip(chip: String) {
        viewModel.handleAction(GameActions.DeleteChip(chip))
    }
}