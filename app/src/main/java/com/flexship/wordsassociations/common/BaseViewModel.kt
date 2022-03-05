package com.flexship.wordsassociations.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<A: Intent, S: State>: ViewModel() {

    abstract val state: MutableStateFlow<S>
    open lateinit var errorHandler: ErrorHandler

    abstract fun handleAction(action: A)
}