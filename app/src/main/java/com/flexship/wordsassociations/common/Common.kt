package com.flexship.wordsassociations.common

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

const val BASE_URL = "https://api.wordassociations.net/"
const val API_KEY = "61f51a50-83f7-43d6-ad15-252261e2c806"

fun hideKeyboardFrom(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun CoroutineScope.launchOnNetwork(
    errorHandler: ErrorHandler,
    action: suspend () -> Unit
) {
    this.launch(Dispatchers.IO) {
        try {
            action()
        } catch (ex: Exception) {
            errorHandler.handleError(ex)
        }
    }

}

inline fun <reified T> tryNull(tryAction: () -> T?): T? {
    return try {
        tryAction()
    } catch (ignored: Exception) {
        null
    }
}

inline fun tryNull(tryAction: () -> Unit): Unit {
    try {
        tryAction()
    } catch (ignored: Exception) {

    }
}

abstract class ItemViewHolder<I : Item>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: I)
}

fun ChipGroup.getActiveChip(): Chip? {
    return findViewById(checkedChipId)
}

@ExperimentalTime
inline fun <reified View: android.view.View> View.viewSuspendAction(delay: Duration, crossinline action: (View) -> Unit) {
    findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.IO) {
        delay(delay)
        withContext(Dispatchers.Main) {
            action(this@viewSuspendAction)
        }
    }
}