package com.github.alunicus.steamclient

import android.app.Application
import com.github.alunicus.steamclient.data.source.DataSource
import com.github.alunicus.steamclient.data.source.Repository
import com.github.alunicus.steamclient.data.source.remote.RemoteDataSource
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

const val REPOSITORY_TAG = "repository"

class Application : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<DataSource>(tag = REPOSITORY_TAG) with singleton { Repository(instance()) }
        bind<DataSource>() with singleton { RemoteDataSource(BuildConfig.BASE_URL) }
    }
}