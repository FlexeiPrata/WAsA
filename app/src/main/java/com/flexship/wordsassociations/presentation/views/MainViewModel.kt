package com.flexship.wordsassociations.presentation.views

import androidx.lifecycle.viewModelScope
import com.flexship.wordsassociations.common.BaseViewModel
import com.flexship.wordsassociations.common.launchOnNetwork
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordsResponseUseCase: GetWordsResponseUseCase
) : BaseViewModel<MenuFragment.MainActions, MenuFragment.MainStates>() {

    override val state: MutableStateFlow<MenuFragment.MainStates> =
        MutableStateFlow(MenuFragment.MainStates.Default)

    private val listOfChips = mutableListOf<String>()

    override fun handleAction(action: MenuFragment.MainActions) {
        when (action) {
            is MenuFragment.MainActions.GetWordsStimulus -> getWords(action.word)
            is MenuFragment.MainActions.AddChip -> listOfChips.add((action.chip))
            is MenuFragment.MainActions.RemoveChip -> listOfChips.remove((action.chip))
        }
    }

    private fun getWords(word: String) {
        viewModelScope.launchOnNetwork(errorHandler) {
            state.value = MenuFragment.MainStates.Loading
            val response = getWordsResponseUseCase(word, listOfChips)
            state.value = MenuFragment.MainStates.SubmitList(response)
        }
    }
}