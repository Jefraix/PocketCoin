package com.jaguiler.pocketcoin.ui.API

data class Coin(
    val id : Int,
    val symbol : String,
    val name : String,
    val nameid : String,
    val rank : Int,
    val price_usd : Double,
    val percent_change_24h : Double,
    val percent_change_1h : Double,
    val percent_change_7d : Double,
    val market_cap_usd : Double,
    val volume24 : Double,
    val volume24_native : Double,
    val csupply : Double,
    val price_btc : Double,
    val tsupply : Double,
    val msupply : Double
)
