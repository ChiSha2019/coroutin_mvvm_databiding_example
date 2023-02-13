package com.myprojects.repository.repo

import com.myprojects.data.AcronymResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface AcronymApi {

    @Throws(IOException::class)
    @GET("acromine/dictionary.py")
    suspend fun getFullForms(@Query("sf") acronym: String): Response<List<AcronymResponse>>
}