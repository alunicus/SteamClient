package com.github.alunicus.steamclient.data.source

import androidx.lifecycle.LiveData
import com.github.alunicus.steamclient.data.Game
import com.github.alunicus.steamclient.data.SearchSuggestion

open class Repository(private val remoteDataSource: DataSource) : DataSource {
    override suspend fun loadGame(id: Long): LiveData<Game> {
        return remoteDataSource.loadGame(id)
    }

    override suspend fun loadSearchSuggestions(searchText: String): List<SearchSuggestion> {
        return remoteDataSource.loadSearchSuggestions(searchText)
    }
}