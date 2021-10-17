package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.common.API_KEY
import com.flexship.wordsassociations.data.models.WordsBasicResponse
import retrofit2.Response
import javax.inject.Inject

class WordsApiHelper @Inject constructor(private val apiService: WordsApiService) {
    suspend fun getWords(word: String, type: String) = apiService.getWords(
        apikey = API_KEY,
        text = word,
        lang = "ru",
        limit = 40,
        type = "response"
    )

    suspend fun getWordsByList(listWords: List<String>, type: String, limit: Int, pos: List<String>) =
        apiService.getWordsByList(
            apikey = API_KEY,
            wordsList = listWords,
            lang = "ru",
            limit = limit,
            type = type,
            pos = pos
        )

    suspend fun getWordsMap(word: List<String>, pos: List<String> , type: String, limit: Int): Response<WordsBasicResponse> {
        val map = HashMap<String, String>()
        map["apikey"] = API_KEY
        map["type"] = type
        map["limit"] = limit.toString()
        map["lang"] = "ru"
        return apiService.getWordsByMap(map, word, pos)
    }
}