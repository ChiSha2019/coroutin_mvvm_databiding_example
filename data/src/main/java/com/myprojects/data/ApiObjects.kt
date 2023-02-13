package com.myprojects.data

import com.google.gson.annotations.SerializedName


data class AcronymResponse(
    @SerializedName("sf") val acronym: String,
    @SerializedName("lfs") val fullForms: List<FullForm>
)

data class FullForm(
    @SerializedName("lf") val name: String,
    @SerializedName("freq") val frequency: String,
    @SerializedName("since") val since: String
)

data class ApiResponse<T>(val data: T? = null, val errorMessage: Int = 0)
