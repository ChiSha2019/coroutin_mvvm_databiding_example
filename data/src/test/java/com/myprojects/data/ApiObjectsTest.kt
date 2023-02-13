package com.myprojects.data

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiObjectsTest {

    @Test
    fun testDataObjects(){
        val fullFormName = "United States of America"
        val since = "1950"
        val freq = "34"
        val fullForm = ApiObjects.FullForm(fullFormName,freq,since)
        val fullForms = listOf(fullForm)
        val apiResponse = ApiObjects.ApiResponse(fullForms)

        Assert.assertNotNull(apiResponse)
        Assert.assertNotNull(apiResponse.data?.firstOrNull())
        Assert.assertEquals(0, apiResponse.errorMessage)
        apiResponse.data?.first()?.let {
            Assert.assertEquals(fullFormName,it.name)
            Assert.assertEquals(since,it.since)
            Assert.assertEquals(freq,it.frequency)
        }

    }
}