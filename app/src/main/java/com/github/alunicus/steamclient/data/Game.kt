package com.github.alunicus.steamclient.data

data class Game(val id: Int,
                val name: String,
                val shortDescription: String,
                val description: String,
                val about: String,
                val cover: String,
                val website: String,
                val developers: List<String>,
                val publishers: List<String>,
                val reviewSummary: ReviewSummary,
                val price: Price,
                val screenshots: List<Screenshot>,
                val videos: List<Video>) {

    val rating = reviewSummary.score.toFloat().div(2)
}