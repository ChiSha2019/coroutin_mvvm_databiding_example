package com.myprojects.data

interface MyRepo {
    suspend fun getFullForms(acronym: String = "") : ApiResponse<List<AcronymResponse>>
}