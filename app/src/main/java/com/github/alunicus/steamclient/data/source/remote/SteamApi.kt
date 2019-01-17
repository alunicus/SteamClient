package com.github.alunicus.steamclient.data.source.remote

import com.github.alunicus.steamclient.data.source.remote.model.ReviewResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SteamApi {
    @GET("api/appdetails?cc=en")
    fun loadGame(@Query("appids") id: Long): Deferred<JsonObject>

    @GET("appreviews/{appId}?json=1&language=all")
    fun loadReviews(@Path("appId") id: Long): Deferred<ReviewResponse>

    @GET("search/suggest?f=games")
    fun loadSearchSuggestions(@Query("term") searchText: String, @Query("cc") currency: String, @Query("l") language: String): Deferred<ResponseBody>
}