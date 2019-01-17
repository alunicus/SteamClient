package com.github.alunicus.steamclient.fullscreenimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.alunicus.steamclient.R
import kotlinx.android.synthetic.main.activity_full_screen_image.*

class FullScreenImageActivity : AppCompatActivity() {

    companion object {
        const val ARG_IMAGE_URL: String = "image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        Glide.with(this).load(intent.getStringExtra(ARG_IMAGE_URL)).into(fullScreenImage)
    }
}
