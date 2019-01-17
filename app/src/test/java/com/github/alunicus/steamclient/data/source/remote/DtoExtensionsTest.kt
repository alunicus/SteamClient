package com.github.alunicus.steamclient.data.source.remote

import com.github.alunicus.steamclient.ResourceReader
import com.github.alunicus.steamclient.data.ReviewSummary
import com.github.alunicus.steamclient.data.source.remote.model.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class DtoExtensionsTest {
    private lateinit var gameData: GameData

    private lateinit var suggestions: String

    private val resourceReader by lazy { ResourceReader() }

    companion object {
        const val GAME_NAME = "name"
        const val REVIEW_SUMMARY_TOTAL = 110
    }

    @Before
    fun setUp() {
        gameData = GameData(
                "type",
                GAME_NAME,
                0,
                18,
                true,
                "detailedDescription",
                "aboutTheGame",
                "shortDescription",
                "supportedLanguages",
                "headerImage",
                "website",
                listOf("developer 1", "developer 2"),
                listOf("publisher 1", "publisher 2"),
                PriceOverview("currency", 0, 1, 0, "0"),
                listOf(ScreenshotResponse(0, "thumbnail", "full")),
                listOf(VideoResponse(0, "name", "thumbnail", VideoUrl("x480", "max"), false)),
                Recommendations(5),
                ReleaseDate(false, "27 \\u0438\\u044e\\u043b. 2017"),
                "background"

        )

        suggestions = resourceReader.getText("search_response.txt")
    }

    @Test
    fun toGame() {
        val reviewSummary = ReviewSummary(100, 10, REVIEW_SUMMARY_TOTAL, 8)

        val game = gameData.toGame(reviewSummary)

        assertEquals(GAME_NAME, game.name)
        assertEquals(2, game.developers.size)
        assertEquals(REVIEW_SUMMARY_TOTAL, game.reviewSummary.total)
    }
}