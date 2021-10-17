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
        MutableStateFlow(MenuFragment.MainStates())


    override fun handleAction(action: MenuFragment.MainActions) {
        when (action) {
            is MenuFragment.MainActions.GetWordsStimulus ->
                viewModelScope.launchOnNetwork(this) {
                    state.value = state.value.copy(
                        isLoading = true
                    )
                    val response = getWordsResponseUseCase(action.word)
                    state.value = state.value.copy(
                        isLoading = false,
                        listForSubmit = response
                    )
                }
        }
    }

    override fun handleHTTPerror() {
        state.value = state.value.copy(isLoading = false)
    }
}