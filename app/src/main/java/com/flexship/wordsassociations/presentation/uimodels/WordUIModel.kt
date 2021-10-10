package com.flexship.wordsassociations.presentation.uimodels

import com.flexship.wordsassociations.common.Item
import com.flexship.wordsassociations.data.models.Words

data class WordUIModel(
    val tag: String,
    val word: Words
): Item {

    override fun id() = tag
    override fun getUIContent() = word

}