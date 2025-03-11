package com.example.mapi.Interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("many/{type}/{category}")
    fun getImages(
        @Path("type") type: String,
        @Path("category") category: String,
        @Body requestData: RequestData
    ): Call<ImagesResponse>
}

data class RequestData(
    val exclude: List<String>
)

data class ImagesResponse(
    val files: List<String>
)
