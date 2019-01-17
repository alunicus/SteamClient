package com.github.alunicus.steamclient.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alunicus.steamclient.data.SearchSuggestion
import com.github.alunicus.steamclient.data.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataSource) : ViewModel() {
    private val search = MutableLiveData<List<SearchSuggestion>>()
    private val error = MutableLiveData<String>()

    private var job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    fun doSearch(searchText: String?) {
        if (!searchText.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val results = repository.loadSearchSuggestions(searchText)
                    search.value = results
                } catch (e: NumberFormatException) {
                    error.value = e.message
                }
            }
        } else {
            closeSearch()
        }
    }

    fun getSearch(): LiveData<List<SearchSuggestion>> {
        return search
    }

    fun getError(): LiveData<String> {
        return error
    }

    fun closeSearch() {
        search.value = listOf()
    }

    override fun onCleared() {
        job.cancel()

        super.onCleared()
    }
}