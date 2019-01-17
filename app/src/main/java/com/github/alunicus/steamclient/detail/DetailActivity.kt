package com.github.alunicus.steamclient.detail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.REPOSITORY_TAG
import com.github.alunicus.steamclient.data.Game
import com.github.alunicus.steamclient.data.Screenshot
import com.github.alunicus.steamclient.data.source.DataSource
import com.github.alunicus.steamclient.fullscreenimage.FullScreenImageActivity
import com.github.alunicus.steamclient.gallery.AllScreenshotsActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class DetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val repository by instance<DataSource>(tag = REPOSITORY_TAG)

    companion object {
        const val ARG_GAME_ID = "game_id"
        const val OFFSCREEN_PAGE_LIMIT = 3
    }

    private lateinit var viewModel: DetailViewModel

    private val adapter: ScreenshotPagerAdapter = ScreenshotPagerAdapter(this) { openFullScreenImage(it) }

    private fun openFullScreenImage(imageUrl: String) {
        val intent = Intent(this@DetailActivity, FullScreenImageActivity::class.java)
        intent.putExtra(FullScreenImageActivity.ARG_IMAGE_URL, imageUrl)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailViewAllScreenshots.setOnClickListener { viewModel.onViewAllScreenshotsClick() }

        detailPager.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT
        detailPager.adapter = adapter

        val gameId = intent.getLongExtra(ARG_GAME_ID, 0)

        viewModel = ViewModelProviders
                .of(this, DetailViewModelFactory(repository, gameId))
                .get(DetailViewModel::class.java)

        viewModel.getGame().observe(this, Observer {
            populateGame(it)
        })

        viewModel.getViewAllScreenshots().observe(this, Observer {
            openAllScreenshotsActivity(it.gameTitle, it.screenshots)
        })

        viewModel.getProgressVisibility().observe(this, Observer {
            detailProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.getGameLayoutVisibility().observe(this, Observer {
            detailGameLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun openAllScreenshotsActivity(title: String, screenshots: List<Screenshot>) {
        val intent = Intent(this@DetailActivity, AllScreenshotsActivity::class.java)
        intent.putExtra(AllScreenshotsActivity.ARG_GAME_NAME, title)
        intent.putExtra(AllScreenshotsActivity.ARG_SCREENSHOTS, Gson().toJson(screenshots))
        startActivity(intent)
    }

    private fun populateGame(game: Game) {
        Glide.with(this).load(game.cover).into(detailGameCover)

        detailGameTitle.text = game.name
        detailGameDescription.setTitle(getString(R.string.game_description))
        detailGameDescription.setText(Html.fromHtml(game.description))
        detailGameWeb.text = Html.fromHtml("<a href=" + game.website + ">Visit website" + "</a>")
        detailGameDeveloper.text = game.developers.toString()
        detailGamePublisher.text = game.publishers.toString()
        detailGamePrice.text = game.price.finalFormatted
        detailGameRating.rating = game.rating
        detailGameRatingNumber.text = String.format("%,d", game.reviewSummary.total)

        adapter.items = game.screenshots
        adapter.notifyDataSetChanged()

        viewModel.onGamePopulated()
    }
}