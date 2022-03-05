package com.flexship.wordsassociations.presentation.views

import androidx.lifecycle.viewModelScope
import com.flexship.wordsassociations.common.BaseViewModel
import com.flexship.wordsassociations.common.launchOnNetwork
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AssociationsViewModel @Inject constructor(
    private val getWordsResponseUseCase: GetWordsResponseUseCase
) : BaseViewModel<AssociationsFragment.MainActions, AssociationsFragment.MainStates>() {

    override val state: MutableStateFlow<AssociationsFragment.MainStates> =
        MutableStateFlow(AssociationsFragment.MainStates.Default)

    override fun handleAction(action: AssociationsFragment.MainActions) {
        when (action) {
            is AssociationsFragment.MainActions.GetWordsStimulus ->
                getWords(
                    action.word,
                    action.chip
                )
        }
    }

    private fun getWords(word: String, chip: String?) {
        viewModelScope.launchOnNetwork(errorHandler) {
            state.value = AssociationsFragment.MainStates.Loading
            val response = getWordsResponseUseCase(word, chip)
            state.value = AssociationsFragment.MainStates.SubmitList(response)
        }
    }
}