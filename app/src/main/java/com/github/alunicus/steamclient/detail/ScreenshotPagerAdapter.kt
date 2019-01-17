package com.github.alunicus.steamclient.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.data.Screenshot

class ScreenshotPagerAdapter(private val context: Context, private val onItemClick: (url: String) -> Unit) : PagerAdapter() {
    var items: List<Screenshot> = listOf()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_screenshot, container, false)

        val item = items[position]

        val cover = view.findViewById<ImageView>(R.id.itemScreenshot)

        cover.setOnClickListener {
            onItemClick(item.fullSizeImage)
        }

        Glide.with(container.context).load(item.thumbnail).into(cover)

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}