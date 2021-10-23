package com.flexship.wordsassociations.presentation.views

import com.flexship.wordsassociations.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChViewModel: BaseViewModel<ChFragment.ChAction, ChFragment.ChState>() {
    override val state: MutableStateFlow<ChFragment.ChState> = MutableStateFlow(ChFragment.ChState.Default)

    override fun handleAction(action: ChFragment.ChAction) {

    }
}