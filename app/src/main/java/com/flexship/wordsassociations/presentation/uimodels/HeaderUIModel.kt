package com.flexship.wordsassociations.presentation.uimodels

import com.flexship.wordsassociations.common.Item

data class HeaderUIModel(
    val tag: String,
    val data: String
) : Item {

    override fun id() = tag

    override fun getUIContent(): MutableList<Any> = mutableListOf(data)

}