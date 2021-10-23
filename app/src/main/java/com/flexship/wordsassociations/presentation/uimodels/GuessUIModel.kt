package com.flexship.wordsassociations.presentation.uimodels

import com.flexship.wordsassociations.common.Item

data class GuessUIModel(val tag: String, val word: String): Item{
    override fun id() = tag

    override fun getUIContent(): MutableList<Any> = mutableListOf(word)

}