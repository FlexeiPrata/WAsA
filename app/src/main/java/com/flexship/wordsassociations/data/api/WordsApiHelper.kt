package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.common.API_KEY
import com.flexship.wordsassociations.data.models.WordsBasicResponse
import retrofit2.Response
import javax.inject.Inject

class WordsApiHelper @Inject constructor(private val apiService: WordsApiService) {
    suspend fun getWordsFromAPI(word: List<String>, pos: List<String>, type: String, limit: Int): WordsBasicResponse {
        val map = HashMap<String, String>()
        map["apikey"] = API_KEY
        map["type"] = type
        map["limit"] = limit.toString()
        map["lang"] = "ru"
        return apiService.getWordsFromAPI(map, word, pos)
    }
}