package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.data.models.WordsBasicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WordsApiService {
    @GET("/associations/v1.0/json/search")
    suspend fun getWords(
        @Query("apikey") apikey: String,
        @Query("text") text: String,
        @Query("lang") lang: String,
        @Query("limit") limit: Int,
        @Query("type") type: String
    ): Response<WordsBasicResponse>
}

