package com.flexship.wordsassociations.presentation.uimodels

import androidx.annotation.StringRes
import com.flexship.wordsassociations.common.Item

data class HeaderUIModel(
    val tag: String,
    @StringRes val data: Int
) : Item {

    override fun id() = tag

    override fun getUIContent(): MutableList<Any> = mutableListOf(data)

}