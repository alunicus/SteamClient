package com.github.alunicus.steamclient.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class AllScreenshotsViewModelFactory(private val jsonString: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllScreenshotsViewModel(jsonString) as T
    }
}