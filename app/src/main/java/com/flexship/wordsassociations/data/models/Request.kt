package com.flexship.wordsassociations.data.models

data class Request(
    val indent: String,
    val lang: String,
    val limit: Int,
    val pos: String,
    val text: List<String>,
    val type: String
)