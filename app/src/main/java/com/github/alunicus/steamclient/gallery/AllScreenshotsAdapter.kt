package com.github.alunicus.steamclient.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.data.Screenshot

class AllScreenshotsAdapter(private val items: List<Screenshot>, private val glide: RequestManager, private val onItemClick: (url: String) -> Unit) : RecyclerView.Adapter<AllScreenshotsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_grid_screenshot, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        glide.load(item.thumbnail).into(holder.image)
        holder.image.setOnClickListener{
            onItemClick(item.fullSizeImage)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.itemScreenshot)
    }
}
