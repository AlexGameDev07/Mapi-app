package com.example.mapi.Interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{type}/{category}")
    fun getImage(
        @Path("type") type: String,
        @Path("category") category: String
    ): Call<ImageResponse>
}

data class ImageResponse(
    val url: String
)
