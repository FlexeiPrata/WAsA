package com.flexship.wordsassociations.presentation.views

import androidx.lifecycle.viewModelScope
import com.flexship.wordsassociations.common.BaseViewModel
import com.flexship.wordsassociations.common.launchOnNetwork
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordsResponseUseCase: GetWordsResponseUseCase
) : BaseViewModel<MenuFragment.MainActions, MenuFragment.MainStates>() {

    override val state: MutableStateFlow<MenuFragment.MainStates> =
        MutableStateFlow(MenuFragment.MainStates())

    private val listOfChips = mutableListOf<String>()

    override fun handleAction(action: MenuFragment.MainActions) {
        when (action) {
            is MenuFragment.MainActions.GetWordsStimulus ->
                viewModelScope.launch(Dispatchers.IO) {
                    state.value = state.value.copy(
                        isLoading = true
                    )
                    val response = getWordsResponseUseCase(action.word, listOfChips)
                    state.value = state.value.copy(
                        isLoading = false,
                        listForSubmit = response
                    )
                }
            is MenuFragment.MainActions.AddChip -> listOfChips.add((action.chip))
            is MenuFragment.MainActions.RemoveChip -> listOfChips.remove((action.chip))
        }
    }


    override fun handleHTTPerror() {
        state.value = state.value.copy(isLoading = false)
    }
}