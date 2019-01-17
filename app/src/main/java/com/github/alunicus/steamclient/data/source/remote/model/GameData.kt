package com.github.alunicus.steamclient.data.source.remote.model

data class GameData(
        val type: String,
        val name: String,
        val steamAppid: Int,
        val requiredAge: Int,
        val isFree: Boolean,
        val detailedDescription: String,
        val aboutTheGame: String,
        val shortDescription: String,
        val supportedLanguages: String,
        val headerImage: String,
        val website: String,
        val developers: List<String>,
        val publishers: List<String>,
        val priceOverview: PriceOverview,
        val screenshots: List<ScreenshotResponse>,
        val movies: List<VideoResponse>,
        val recommendations: Recommendations,
        val releaseDate: ReleaseDate,
        val background: String
)