package com.github.alunicus.steamclient.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.alunicus.steamclient.data.source.DataSource


class DetailViewModelFactory(private val repository: DataSource, private val gameId: Long) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository, gameId) as T
    }
}