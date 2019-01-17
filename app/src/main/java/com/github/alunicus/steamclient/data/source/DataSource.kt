package com.github.alunicus.steamclient.data.source

import androidx.lifecycle.LiveData
import com.github.alunicus.steamclient.data.Game
import com.github.alunicus.steamclient.data.SearchSuggestion

interface DataSource {
    suspend fun loadGame(id: Long): LiveData<Game>

    suspend fun loadSearchSuggestions(searchText: String): List<SearchSuggestion>
}