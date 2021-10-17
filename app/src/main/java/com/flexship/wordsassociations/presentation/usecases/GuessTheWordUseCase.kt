package com.flexship.wordsassociations.presentation.usecases

interface GuessTheWordUseCase {
    suspend operator fun invoke(list: List<String>): List<String>
}