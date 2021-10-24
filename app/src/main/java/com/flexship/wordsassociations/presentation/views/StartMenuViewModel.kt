package com.flexship.wordsassociations.presentation.views

import com.flexship.wordsassociations.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class StartMenuViewModel: BaseViewModel<StartMenuFragment.ChAction, StartMenuFragment.ChState>() {
    override val state: MutableStateFlow<StartMenuFragment.ChState> = MutableStateFlow(StartMenuFragment.ChState.Default)

    override fun handleAction(action: StartMenuFragment.ChAction) {

    }
}