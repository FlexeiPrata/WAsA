package com.flexship.wordsassociations.presentation.views

import androidx.lifecycle.viewModelScope
import com.flexship.wordsassociations.common.BaseViewModel
import com.flexship.wordsassociations.common.launchOnNetwork
import com.flexship.wordsassociations.presentation.usecases.GuessTheWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private var guessTheWordUseCase: GuessTheWordUseCase
) : BaseViewModel<GameFragment.GameActions, GameFragment.GameState>() {
    override val state = MutableStateFlow(GameFragment.GameState())

    val listOfGuess = mutableListOf<String>()

    override fun handleAction(action: GameFragment.GameActions) {
        when (action) {
            is GameFragment.GameActions.AddWord -> {
                listOfGuess.add(action.word)
                println("DEBUG:: $listOfGuess")
                state.value = state.value.copy(wordsList = listOfGuess.toMutableList())
            }
            GameFragment.GameActions.Clear -> {
                listOfGuess.clear()
                state.value = state.value.copy(wordsList = mutableListOf())
            }
            GameFragment.GameActions.Guess -> {
                viewModelScope.launchOnNetwork(errorHandler) {
                    state.value = state.value.copy(isLoading = true)
                    val response = guessTheWordUseCase(listOfGuess)
                    state.value = state.value.copy(guessWord = response.toMutableList())
                    state.value = state.value.copy(isLoading = false)
                }

            }
        }
    }
}