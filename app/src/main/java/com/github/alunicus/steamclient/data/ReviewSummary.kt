package com.github.alunicus.steamclient.data

data class ReviewSummary(
        val positive: Int,
        val negative: Int,
        val total: Int,
        val score: Int
)