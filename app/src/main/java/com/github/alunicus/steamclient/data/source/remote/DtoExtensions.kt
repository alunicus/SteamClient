package com.github.alunicus.steamclient.data.source.remote

import com.github.alunicus.steamclient.data.*
import com.github.alunicus.steamclient.data.source.remote.model.*
import okhttp3.ResponseBody

fun GameData.toGame(reviewSummary: ReviewSummary) = Game(
        steamAppid,
        name,
        shortDescription,
        detailedDescription,
        aboutTheGame,
        headerImage,
        website,
        developers,
        publishers,
        reviewSummary,
        priceOverview.toPrice(),
        screenshots.toScreenshots(),
        movies.toVideos()
)

fun ReviewResponse.toReview() = ReviewSummary(
        querySummary.totalPositive,
        querySummary.totalNegative,
        querySummary.totalReviews,
        querySummary.reviewScore
)


private fun PriceOverview?.toPrice() = if (this == null) Price() else Price(
        this.initial,
        this.final,
        this.discountPercent,
        this.finalFormatted)

private fun ScreenshotResponse.toScreenshot() = Screenshot(id, pathThumbnail, pathFull)

private fun List<ScreenshotResponse>.toScreenshots() = this.map { it.toScreenshot() }.toList()

private fun VideoResponse.toVideo() = Video(id, name, thumbnail, webm.max)

private fun List<VideoResponse>.toVideos() = this.map { it.toVideo() }.toList()

fun ResponseBody.toSearchSuggestions(): List<SearchSuggestion> = string().let {
    val items = it.split("</a><a")

    return items.map { item ->
        val idKeyword = if (item.contains("data-ds-bundleid=\"")) "data-ds-bundleid=\"" else "data-ds-appid=\""

        val id = item.substringAfter(idKeyword).substringBefore("\"").toLong()
        val name = item.substringAfter("match_name\">").substringBefore("</div>")
        val url = item.substringAfter("match_img\"><img src=\"").substringBefore("\"")
        val price = item.substringAfter("match_price\">").substringBefore("</div>")

        SearchSuggestion(id, name, url, price)
    }
}