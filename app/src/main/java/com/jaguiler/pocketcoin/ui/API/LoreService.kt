package com.jaguiler.pocketcoin.ui.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoreService {

    @GET("api/ticker/")
    fun getCoinById(
        @Query("id") coinID: String
    ): Call<List<Coin>>
}