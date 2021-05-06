package com.jaguiler.pocketcoin.ui.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoreService {

    @GET("/api/ticker/")
    fun getCoinById(
        @Query("id") coinID: Int
    ): Call<List<Coin>>

    @GET("/api/tickers/")
    fun getTop20Coins(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): Call<Coinlist>
}