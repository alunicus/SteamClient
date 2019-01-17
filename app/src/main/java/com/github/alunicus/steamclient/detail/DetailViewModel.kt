package com.github.alunicus.steamclient.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.alunicus.steamclient.data.Game
import com.github.alunicus.steamclient.data.GameScreenshots
import com.github.alunicus.steamclient.data.source.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DataSource, private val gameId: Long) : ViewModel() {
    private var job = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    private val game = MediatorLiveData<Game>()
    private val viewAllScreenshots = MutableLiveData<GameScreenshots>()
    private val progressVisibility = MutableLiveData<Boolean>()
    private val gameLayoutVisibility = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            game.addSource(repository.loadGame(gameId), game::setValue)
        }
    }

    fun getGame(): LiveData<Game> {
        return game
    }

    fun getViewAllScreenshots(): LiveData<GameScreenshots> {
        return viewAllScreenshots
    }

    fun getProgressVisibility(): LiveData<Boolean> {
        return progressVisibility
    }

    fun getGameLayoutVisibility(): LiveData<Boolean> {
        return gameLayoutVisibility
    }

    fun onViewAllScreenshotsClick() {
        val gameValue = game.value ?: return

        viewAllScreenshots.postValue(GameScreenshots(gameValue.name, gameValue.screenshots))
    }

    fun onGamePopulated() {
        progressVisibility.value = false
        gameLayoutVisibility.value = true
    }

    override fun onCleared() {
        job.cancel()

        super.onCleared()
    }
}