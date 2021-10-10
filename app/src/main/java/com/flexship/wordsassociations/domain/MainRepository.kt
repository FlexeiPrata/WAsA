package com.flexship.wordsassociations.domain

import com.flexship.wordsassociations.data.models.WordsBasicResponse

interface MainRepository {
    suspend fun getWordsResponse(word: String): WordsBasicResponse?
}