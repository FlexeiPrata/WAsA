package com.flexship.wordsassociations.domain

import com.flexship.wordsassociations.presentation.usecases.GuessTheWordUseCase
import javax.inject.Inject

class GuessTheWordUseCaseImpl @Inject constructor(private val repository: MainRepository) :
    GuessTheWordUseCase {

    override suspend fun invoke(list: List<String>): List<String> {
        repository.getGuessWords(list)?.let {
            val items = it.response.map { response ->
                response.items.map { words ->
                    words.item
                }
            }
            return items.reduce { accumulated, next ->
                val inter = accumulated.intersect(next).toList()
                inter.ifEmpty { accumulated }
            }
        }
        return emptyList()
    }
}