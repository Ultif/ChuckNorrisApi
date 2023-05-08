package com.example.chucknorrisapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckJokesService {
    @GET("jokes/random")
    fun getRandomFactByCategory(@Query("category") category: String):  Call<ChuckNorrisResponse>
}