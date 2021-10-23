package com.flexship.wordsassociations.domain

import com.flexship.wordsassociations.R
import com.flexship.wordsassociations.common.Item
import com.flexship.wordsassociations.data.models.Words
import com.flexship.wordsassociations.presentation.uimodels.HeaderUIModel
import com.flexship.wordsassociations.presentation.uimodels.WordUIModel
import com.flexship.wordsassociations.presentation.usecases.GetWordsResponseUseCase
import javax.inject.Inject

class GetWordsResponseUseCaseImpl @Inject constructor(private val repository: MainRepository) :
    GetWordsResponseUseCase {

    override suspend fun invoke(word: String, pos: List<String>): List<Item> {
        val response = repository.getWordsResponse(word, pos)
        val list = mutableListOf<Words>()
        list.addAll(response?.response?.get(0)?.items ?: emptyList())

        return mutableListOf<Item>().apply {
            val strong = list.filter { it.weight > 70 }
            if (strong.isNotEmpty()) {
                add(HeaderUIModel("header1", R.string.fr_70))
                addAll(strong.mapIndexed { index, words -> WordUIModel("strong$index", words) })
            }
            val medium = list.filter { it.weight in 31..70 }
            if (medium.isNotEmpty()) {
                add(HeaderUIModel("header2", R.string.fr_30))
                addAll(medium.mapIndexed { index, words -> WordUIModel("medium$index", words) })
            }
            val weak = list.filter { it.weight <= 30 }
            if (medium.isNotEmpty()) {
                add(HeaderUIModel("header2", R.string.fr_0))
                addAll(weak.mapIndexed { index, words -> WordUIModel("weak$index", words) })
            }
        }

    }
}