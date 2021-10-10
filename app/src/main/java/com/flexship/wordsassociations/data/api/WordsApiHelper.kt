package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.common.API_KEY
import javax.inject.Inject

class WordsApiHelper @Inject constructor(private val apiService: WordsApiService) {
    suspend fun getWords(word: String, type: String) = apiService.getWords(
        apikey = API_KEY,
        text = word,
        lang = "ru",
        limit = 20,
        type = "response"
    )
}