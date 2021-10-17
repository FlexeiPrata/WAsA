package com.flexship.wordsassociations.presentation.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.flexship.wordsassociations.R
import com.flexship.wordsassociations.common.Action
import com.flexship.wordsassociations.common.BaseFragment
import com.flexship.wordsassociations.common.State
import com.flexship.wordsassociations.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding, GameFragment.GameState>() {

    private val viewModel: GameViewModel by viewModels()

    data class GameState(
        override var isLoading: Boolean = false,
        var wordsList: MutableList<String> = mutableListOf(),
        var guessWord: List<String> = mutableListOf()
    ) : State

    sealed class GameActions : Action {
        data class AddWord(val word: String) : GameActions()
        object Clear : GameActions()
        object Guess : GameActions()
    }

    override fun setupViews() {
        binding.apply {
            guessLabel.isVisible = false
            guessText.isVisible = false
            buttonAdd.setOnClickListener {
                val text = textInput.editableText.toString()
                if (viewModel.listOfGuess.size <= 10 && text.isNotEmpty()) {
                    viewModel.handleAction(GameActions.AddWord(text))
                    textInput.editableText.clear()
                }
            }
            guess.setOnClickListener {
                viewModel.handleAction(GameActions.Guess)
            }
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
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding {
        return FragmentGameBinding.inflate(inflater, container, false)
    }

    override fun render(state: GameState) {
        binding.apply {
            loadingBar.isVisible = state.isLoading
            val final = StringBuilder(
                String.format(
                    getString(R.string.guess_text),
                    viewModel.listOfGuess.size
                )
            )
            state.wordsList.forEachIndexed { index, s ->
                final.append("\n${index + 1}. $s")
                println("DEBUG:: $s")
            }
            textView.text = final
            guessLabel.isVisible = !state.guessWord.isNullOrEmpty()
            guessText.isVisible = !state.guessWord.isNullOrEmpty()
            guessLabel2.visibility = if (state.guessWord.size > 1) View.VISIBLE else View.INVISIBLE
            guessTextMinor.visibility =
                if (state.guessWord.size > 1) View.VISIBLE else View.INVISIBLE
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
}