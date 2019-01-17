package com.github.alunicus.steamclient.data

data class Price(
        val initial: Int = 0,
        val final: Int = 0,
        val discountPercent: Int = 0,
        val finalFormatted: String = ""
)