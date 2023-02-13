package com.myprojects.repository.repo

import com.myprojects.data.AcronymResponse
import com.myprojects.data.FullForm
import com.myprojects.data.MyRepo
import com.myprojects.repository.R
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response
import java.net.UnknownHostException

class MyRepoImplTest {

    private lateinit var repository: MyRepo
    private val acronymApi: AcronymApi = mock()

    @Before
    fun setUp(){
        repository = MyRepoImpl(acronymApi)
    }

    @Test
    fun testSuccess(){
        val acronym = "USA"
        val fullForm = FullForm("United States of America","450","1950")
        runBlocking {
            Mockito.`when`(acronymApi.getFullForms(acronym)).thenReturn(Response.success(listOf(
                AcronymResponse(acronym, listOf(fullForm))
            )))
            val apiResponse = repository.getFullForms(acronym)
            Assert.assertNotNull(apiResponse)
            Assert.assertEquals(fullForm,apiResponse.data?.firstOrNull()?.fullForms?.firstOrNull())
            Assert.assertEquals(0,apiResponse.errorMessage)
        }
    }

    @Test
    fun testErrorWithEmptyPayload(){
        val acronym = "USA"
        runBlocking {
            Mockito.`when`(acronymApi.getFullForms(acronym)).thenReturn(Response.success(emptyList()))
            val apiResponse = repository.getFullForms(acronym)
            Assert.assertNotNull(apiResponse)
            Assert.assertNull(apiResponse.data)
            Assert.assertEquals(R.string.no_data_found,apiResponse.errorMessage)
        }
    }

    @Test
    fun testFailure(){
        val acronym = "USA"
        runBlocking {
            Mockito.`when`(acronymApi.getFullForms(acronym)).thenReturn(Response.error(500,
                ResponseBody.create(null,"")))
            val apiResponse = repository.getFullForms(acronym)
            Assert.assertNotNull(apiResponse)
            Assert.assertNull(apiResponse.data)
            Assert.assertEquals(R.string.un_expected_error,apiResponse.errorMessage)
        }
    }

    @Test
    fun testExceptionFailure(){
        val acronym = "USA"
        runBlocking {
            Mockito.doThrow(UnknownHostException::class.java).`when`(acronymApi).getFullForms(acronym)
            val apiResponse = repository.getFullForms(acronym)
            Assert.assertNotNull(apiResponse)
            Assert.assertNull(apiResponse.data)
            Assert.assertEquals(R.string.un_expected_error,apiResponse.errorMessage)
        }
    }

}