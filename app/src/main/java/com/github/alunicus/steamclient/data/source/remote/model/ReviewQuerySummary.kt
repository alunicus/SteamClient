package com.github.alunicus.steamclient.data.source.remote.model

data class ReviewQuerySummary(
		val numReviews: Int,
		val reviewScore: Int,
		val reviewScoreDesc: String,
		val totalPositive: Int,
		val totalNegative: Int,
		val totalReviews: Int
)