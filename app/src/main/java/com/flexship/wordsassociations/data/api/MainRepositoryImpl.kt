package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.data.models.WordsBasicResponse
import com.flexship.wordsassociations.domain.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val helper: WordsApiHelper): MainRepository {
    override suspend fun getWordsResponse(word: String): WordsBasicResponse? {
        return helper.getWords(word, "response").body()
    }
}