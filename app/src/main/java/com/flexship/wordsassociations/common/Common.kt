package com.flexship.wordsassociations.common

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val BASE_URL = "https://api.wordassociations.net/"
const val API_KEY = "61f51a50-83f7-43d6-ad15-252261e2c806"

fun hideKeyboardFrom(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun CoroutineScope.launchOnNetwork(
    viewModel: BaseViewModel<* ,*>,
    action: suspend () -> Unit
) {
    this.launch(Dispatchers.IO) {
        try {
            action()
        } catch (ex: Exception) {
            viewModel.handleHTTPerror()
        }
    }

}

