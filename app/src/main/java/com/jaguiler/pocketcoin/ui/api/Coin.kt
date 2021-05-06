package com.jaguiler.pocketcoin.ui.api

import java.math.BigDecimal

data class Coin(
    val id : Int = -1,
    val symbol : String = "",
    val name : String = "",
    val nameid : String = "",
    val rank : Int = -1,
    val price_usd : Double = -1.0,
    val percent_change_24h : Double = 0.0,
    val percent_change_1h : Double = 0.0,
    val percent_change_7d : Double = 0.0,
    val market_cap_usd : BigDecimal = 0.0.toBigDecimal(),
    val volume24 : BigDecimal = 0.0.toBigDecimal(),
    val volume24_native : BigDecimal = 0.0.toBigDecimal(),
    val csupply : BigDecimal = 0.0.toBigDecimal(),
    val price_btc : BigDecimal = 0.0.toBigDecimal(),
    val tsupply : BigDecimal = 0.0.toBigDecimal(),
    val msupply : String = ""
)

data class Coinlist(
    val data: List<Coin> = listOf()
)
