package com.flexship.wordsassociations.presentation.usecases

import com.flexship.wordsassociations.common.Item

interface GetWordsResponseUseCase {
    suspend operator fun invoke(word: String, pos: String?): List<Item>
}