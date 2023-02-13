package com.myprojects.repository.repo

import android.util.Log
import com.myprojects.data.MyRepo
import com.myprojects.data.ApiResponse
import com.myprojects.data.AcronymResponse
import com.myprojects.repository.R
import javax.inject.Inject

class MyRepoImpl @Inject constructor(private val acronymApi: AcronymApi): MyRepo {

    override suspend fun getFullForms(acronym: String): ApiResponse<List<AcronymResponse>> {
        return try {
            val response = acronymApi.getFullForms(acronym)
            Log.d(TAG,"Response: ${response.body()}")
            when{
                !response.isSuccessful -> {
                    Log.e(TAG,"Error Response: ${response.errorBody()}")
                    ApiResponse(errorMessage = R.string.un_expected_error)
                }
                !response.body().isNullOrEmpty() ->  ApiResponse(response.body())
                else -> ApiResponse(errorMessage = R.string.no_data_found)
            }
        }catch (ex: Exception){
            Log.e(TAG,"Error: ${ex.stackTraceToString()}")
            ApiResponse(errorMessage = R.string.un_expected_error)
        }
    }

    companion object{
        val TAG: String = MyRepoImpl::class.java.simpleName
    }
}