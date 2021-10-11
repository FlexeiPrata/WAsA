package com.flexship.wordsassociations.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

const val BASE_URL = "https://api.wordassociations.net/"
const val API_KEY = "61f51a50-83f7-43d6-ad15-252261e2c806"

fun hideKeyboardFrom(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

