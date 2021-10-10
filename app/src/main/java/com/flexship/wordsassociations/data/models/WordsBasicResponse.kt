package com.flexship.wordsassociations.data.models

data class WordsBasicResponse(
    val code: Int,
    val request: Request,
    val response: List<Response>,
    val version: String
)