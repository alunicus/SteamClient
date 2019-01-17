package com.github.alunicus.steamclient.data.source.remote.model

data class PriceOverview(
        val currency: String,
        val initial: Int,
        val final: Int,
        val discountPercent: Int,
        val finalFormatted: String
)