package com.flexship.wordsassociations.presentation.views

import androidx.lifecycle.viewModelScope
import com.flexship.wordsassociations.common.BaseViewModel
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWordsResponseUseCase: GetWordsResponseUseCase
) : BaseViewModel<MenuFragment.MainActions, MenuFragment.MainStates>() {

    override val state: MutableStateFlow<MenuFragment.MainStates> =
        MutableStateFlow(MenuFragment.MainStates.Static)


    override fun handleAction(action: MenuFragment.MainActions) {
        when (action) {
            is MenuFragment.MainActions.GetWordsStimulus -> viewModelScope.launch(Dispatchers.IO) {
                state.value = MenuFragment.MainStates.Loading
                val response = getWordsResponseUseCase(action.word)
                println("Size = ${response.size}")
                state.value =
                    MenuFragment.MainStates.SetupWords(response)
            }
        }
    }
}