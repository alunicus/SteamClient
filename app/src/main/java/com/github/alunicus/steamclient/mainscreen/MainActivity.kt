package com.github.alunicus.steamclient.mainscreen

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.REPOSITORY_TAG
import com.github.alunicus.steamclient.data.source.DataSource
import com.github.alunicus.steamclient.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    private val TAG by lazy { MainActivity::class.java.simpleName }

    override val kodein: Kodein by closestKodein()

    private val repository by instance<DataSource>(tag = REPOSITORY_TAG)

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: SearchSuggestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolbar)

        adapter = SearchSuggestionAdapter(Glide.with(this)) {
            val intent = android.content.Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.ARG_GAME_ID, it)
            startActivity(intent)
        }

        mainSearchSuggestions.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(mainSearchSuggestions.context, DividerItemDecoration.VERTICAL)
        val divider = ContextCompat.getDrawable(mainSearchSuggestions.context, R.drawable.divider)

        if (divider != null) {
            dividerItemDecoration.setDrawable(divider)
            mainSearchSuggestions.addItemDecoration(dividerItemDecoration)
        }

        viewModel = ViewModelProviders
                .of(this, MainViewModelFactory(repository))
                .get(MainViewModel::class.java)

        viewModel.getSearch().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        viewModel.getError().observe(this, Observer {
            Log.e(TAG, "Failed to load search suggestions: $it")
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchMenuItem = menu.findItem(R.id.search)

        (searchMenuItem.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.doSearch(newText)
                    return true
                }

            })
        }

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?) = true

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.closeSearch()

                return true
            }

        })

        return true
    }
}