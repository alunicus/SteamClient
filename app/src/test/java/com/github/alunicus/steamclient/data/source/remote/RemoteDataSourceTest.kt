package com.github.alunicus.steamclient.data.source.remote

import com.github.alunicus.steamclient.ResourceReader
import com.github.alunicus.steamclient.data.source.DataSource
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class RemoteDataSourceTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var dataSource: DataSource
    private val resourceReader = ResourceReader()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        dataSource = RemoteDataSource(mockServer.url("/").toString())
    }

    @Test
    fun loadSearchSuggestions() = runBlocking {
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(resourceReader.getText("search_response.txt"))

        mockServer.enqueue(mockResponse)


        val result = dataSource.loadSearchSuggestions("search")

        assertEquals(5, result.size)
        assertEquals(result[0].appId, 581360)
        assertEquals(result[1].name, "Foxhole")
        assertEquals(result[2].thumbnail, "https://steamcdn-a.akamaihd.net/steam/apps/844930/capsule_sm_120.jpg?t=1535097694")
        assertEquals(result[3].price, "1,99€")
    }
}