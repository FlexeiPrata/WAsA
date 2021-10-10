package com.flexship.wordsassociations.presentation.usecases

import com.flexship.wordsassociations.data.models.Words

interface GetWordsResponseUseCase {
    suspend operator fun invoke(word: String): List<Words>
}