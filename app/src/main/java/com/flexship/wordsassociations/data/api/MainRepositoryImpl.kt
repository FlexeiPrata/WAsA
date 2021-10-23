package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.data.models.WordsBasicResponse
import com.flexship.wordsassociations.domain.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val helper: WordsApiHelper): MainRepository {
    override suspend fun getWordsResponse(word: String, pos: List<String>): WordsBasicResponse? {
        return helper.getWordsFromAPI(word = listOf(word, ""), pos = pos, limit = 200, type = "stimulus")
    }

    override suspend fun getGuessWords(listOfWords: List<String>): WordsBasicResponse? {
        return helper.getWordsFromAPI(word = listOfWords, pos = listOf("noun"), "response", limit = 300)
    }
}