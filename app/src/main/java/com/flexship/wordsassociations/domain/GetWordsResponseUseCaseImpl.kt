package com.flexship.wordsassociations.domain

import com.flexship.wordsassociations.data.models.Words
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import javax.inject.Inject

class GetWordsResponseUseCaseImpl @Inject constructor(private val repository: MainRepository) :
    GetWordsResponseUseCase {

    override suspend fun invoke(word: String): List<Words> {
        val response = repository.getWordsResponse(word)
        val list = mutableListOf<Words>()
        list.addAll(response?.response?.get(0)?.items ?: emptyList())

        return list
    }

}