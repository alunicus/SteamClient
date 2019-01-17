package com.github.alunicus.steamclient.mainscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.alunicus.steamclient.data.SearchSuggestion
import com.github.alunicus.steamclient.data.source.Repository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<SearchSuggestion>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun doSearch() = runBlocking {
        val suggestions = listOf(SearchSuggestion(0, "0", "1", "2"))

        val dataSource = mock<Repository> {
            on { runBlocking { loadSearchSuggestions("search") } } doReturn suggestions
        }

        val viewModel = MainViewModel(dataSource)

        val searchLiveData = viewModel.getSearch()

        searchLiveData.observeForever(observer)

        viewModel.doSearch("search")

        verify(observer).onChanged(suggestions)
    }
}