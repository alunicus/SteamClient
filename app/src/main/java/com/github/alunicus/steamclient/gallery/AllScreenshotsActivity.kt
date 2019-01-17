package com.github.alunicus.steamclient.gallery

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.data.Screenshot
import com.github.alunicus.steamclient.fullscreenimage.FullScreenImageActivity
import kotlinx.android.synthetic.main.activity_all_screenshots.*

class AllScreenshotsActivity : AppCompatActivity() {

    companion object {
        const val ARG_GAME_NAME: String = "gameName"
        const val ARG_SCREENSHOTS: String = "screenshots"
    }

    private lateinit var viewModel: AllScreenshotsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_screenshots)

        setSupportActionBar(allScreenshotsToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(ARG_GAME_NAME) ?: ""

        val screenshotsJson = intent.getStringExtra(AllScreenshotsActivity.ARG_SCREENSHOTS)

        viewModel = ViewModelProviders
                .of(this, AllScreenshotsViewModelFactory(screenshotsJson))
                .get(AllScreenshotsViewModel::class.java)

        viewModel.screenshots.observe(this, Observer {
            screenshotList.adapter = initAdapter(it)
        })
    }

    private fun initAdapter(items: List<Screenshot>): AllScreenshotsAdapter {
        return AllScreenshotsAdapter(items, Glide.with(this)) {
            val intent = Intent(this@AllScreenshotsActivity, FullScreenImageActivity::class.java)
            intent.putExtra(FullScreenImageActivity.ARG_IMAGE_URL, it)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}