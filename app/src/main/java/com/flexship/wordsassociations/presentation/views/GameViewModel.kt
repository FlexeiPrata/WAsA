package com.flexship.wordsassociations.presentation.views

import androidx.lifecycle.viewModelScope
import com.flexship.wordsassociations.common.BaseViewModel
import com.flexship.wordsassociations.common.launchOnNetwork
import com.flexship.wordsassociations.presentation.uimodels.GuessUIModel
import com.flexship.wordsassociations.presentation.usecases.GuessTheWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private var guessTheWordUseCase: GuessTheWordUseCase
) : BaseViewModel<GameFragment.GameActions, GameFragment.GameState>() {
    override val state: MutableStateFlow<GameFragment.GameState> =
        MutableStateFlow(GameFragment.GameState.Default)

    val listOfGuess = mutableListOf<String>()

    override fun handleAction(action: GameFragment.GameActions) {
        when (action) {
            is GameFragment.GameActions.AddWord -> {
                listOfGuess.add(action.word)
                state.value = GameFragment.GameState.SubmitWords(listOfGuess.mapToWordsUIModel())
            }
            GameFragment.GameActions.Guess -> guess()
            is GameFragment.GameActions.DeleteChip -> {
                listOfGuess.remove(action.chip)
                state.value = GameFragment.GameState.SubmitWords(listOfGuess.mapToWordsUIModel())
            }
            GameFragment.GameActions.Clear -> {
                listOfGuess.clear()
                state.value = GameFragment.GameState.Cleared
            }
        }
    }

    private fun MutableList<String>.mapToWordsUIModel(): List<GuessUIModel> {
        return this.map {
            GuessUIModel("guess$it", it)
        }
    }

    private fun guess() {
        viewModelScope.launchOnNetwork(errorHandler) {
            state.value = GameFragment.GameState.Loading
            val response = guessTheWordUseCase(listOfGuess)
            state.value = GameFragment.GameState.SubmitHunch(response)
        }
    }
}