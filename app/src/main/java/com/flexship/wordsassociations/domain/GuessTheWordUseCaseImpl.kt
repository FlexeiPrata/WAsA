package com.flexship.wordsassociations.domain

import com.flexship.wordsassociations.common.Item
import com.flexship.wordsassociations.data.models.Words
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import com.flexship.wordsassociations.presentation.usecases.GuessTheWordUseCase
import javax.inject.Inject

class GuessTheWordUseCaseImpl @Inject constructor(private val repository: MainRepository) :
    GuessTheWordUseCase {

    override suspend fun invoke(list: List<String>): List<String> {
        repository.getGuessWords(list)?.let {
            val set = mutableSetOf<List<String>>()
            val items = it.response.map {
                it.items.map {
                    it.item
                }
            }
            println("DEBUG:: items = $items")
            val a = items.reduce { acc, list ->
                val inter = acc.intersect(list).toList()
                if (inter.isNotEmpty()) inter
                else acc
            }
            println("DEBUG:: $a")
            return a
        }
        return emptyList()
    }
}