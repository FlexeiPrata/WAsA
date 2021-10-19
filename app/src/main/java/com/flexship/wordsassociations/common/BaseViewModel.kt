package com.flexship.wordsassociations.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

abstract class BaseViewModel<A: Action, S: State>: ViewModel() {

    abstract val state: MutableStateFlow<S>
    open lateinit var errorHandler: ErrorHandler

    abstract fun handleAction(action: A)
    open fun handleHTTPerror(ex: Exception){

    }
}