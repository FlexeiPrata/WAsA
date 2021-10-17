package com.flexship.wordsassociations.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<A: Action, S: State>: ViewModel() {

    abstract val state: MutableStateFlow<S>

    abstract fun handleAction(action: A)
    open fun handleHTTPerror(){

    }
}