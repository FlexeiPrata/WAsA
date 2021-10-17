package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.data.models.WordsBasicResponse
import com.flexship.wordsassociations.domain.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val helper: WordsApiHelper): MainRepository {
    override suspend fun getWordsResponse(word: String, pos: List<String>): WordsBasicResponse? {
        //return helper.getWordsByList(listWords = listOf(word), pos = pos, limit = 40, type = "response").body()
        return helper.getWordsMap(word = listOf(word, ""), pos = pos, limit = 200, type = "stimulus").body()
    }

    override suspend fun getGuessWords(listOfWords: List<String>): WordsBasicResponse? {
        return helper.getWordsMap(word = listOfWords, pos = listOf("noun"), "response", limit = 300).body()
    }
}