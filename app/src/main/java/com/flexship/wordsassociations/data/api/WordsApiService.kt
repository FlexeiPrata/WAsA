package com.flexship.wordsassociations.data.api

import com.flexship.wordsassociations.data.models.WordsBasicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WordsApiService {

    @GET("/associations/v1.0/json/search")
    suspend fun getWordsFromAPI(
        @QueryMap paramsMap: Map<String, String>,
        @Query("text") text: List<String>,
        @Query("pos") pos: List<String>
    ): WordsBasicResponse

}

