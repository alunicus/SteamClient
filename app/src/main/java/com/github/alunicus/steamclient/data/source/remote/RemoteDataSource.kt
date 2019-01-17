package com.github.alunicus.steamclient.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.alunicus.steamclient.data.Game
import com.github.alunicus.steamclient.data.SearchSuggestion
import com.github.alunicus.steamclient.data.source.DataSource
import com.github.alunicus.steamclient.data.source.remote.model.GameContainer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource(baseUrl: String,
                       private val currency: String = "us",
                       private val language: String = "english") : DataSource {

    private val api: SteamApi

    private val gson: Gson by lazy {
        GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create()
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        api = retrofit.create<SteamApi>(SteamApi::class.java)
    }

    override suspend fun loadGame(id: Long): LiveData<Game> {

        val data = MutableLiveData<Game>()

        val gameResponse = api.loadGame(id).await()
        val gameData = gson.fromJson(gameResponse.getAsJsonObject(id.toString()), GameContainer::class.java).data

        val reviewResponse = api.loadReviews(id).await()
        val reviewSummary = reviewResponse.toReview()

        data.value = gameData.toGame(reviewSummary)

        return data
    }

    override suspend fun loadSearchSuggestions(searchText: String): List<SearchSuggestion> {
        return api.loadSearchSuggestions(searchText, currency, language).await().toSearchSuggestions()
    }
}
