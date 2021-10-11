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

    override suspend fun invoke(word: String): List<Item> {
        val response = repository.getWordsResponse(word)
        val list = mutableListOf<Words>()
        list.addAll(response?.response?.get(0)?.items ?: emptyList())
        return mutableListOf<Item>(HeaderUIModel("header1", R.string.fr_70)).apply {

            addAll(
                list.filter {
                it.weight > 70
            }.mapIndexed { index, words -> WordUIModel("top$index", words) })

            add(HeaderUIModel("header2", R.string.fr_30))

            addAll(
                list.filter {
                it.weight <= 70
            }.mapIndexed { index, words -> WordUIModel("bottom$index", words) })

        }


    }

}