package com.kaliostech.imoviefinder

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    suspend fun getMovieInfo(
        @Query("apikey") apiKey: String,
        @Query("t") title: String,
        @Query("y") year: String?,
        @Query("plot") plot: String
    ): Response<MovieInfo>
}