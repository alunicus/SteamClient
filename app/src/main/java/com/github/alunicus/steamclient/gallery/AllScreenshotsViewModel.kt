package com.github.alunicus.steamclient.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alunicus.steamclient.data.Screenshot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AllScreenshotsViewModel(screenshotsJson: String) : ViewModel() {
    val screenshots = MutableLiveData<List<Screenshot>>()

    init {
        val listType = object : TypeToken<List<Screenshot>>() {}.type
        val items = Gson().fromJson<List<Screenshot>>(screenshotsJson, listType)

        this.screenshots.postValue(items)
    }
}