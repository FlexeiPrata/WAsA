package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.common.API_KEY
import javax.inject.Inject

class WordsApiHelper @Inject constructor(private val apiService: WordsApiService) {
    suspend fun getWords(word: String, type: String) = apiService.getWords(
        apikey = API_KEY,
        text = word,
        lang = "ru",
        limit = 40,
        type = "response"
    )

    suspend fun getWordsByList(listWords: List<String>, type: String, limit: Int) =
        apiService.getWordsByList(
            apikey = API_KEY,
            wordsList = listWords,
            lang = "ru",
            limit = limit,
            type = type
        )

}