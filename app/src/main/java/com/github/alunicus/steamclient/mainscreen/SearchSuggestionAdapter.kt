package com.github.alunicus.steamclient.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.data.SearchSuggestion

class SearchSuggestionAdapter(private val glide: RequestManager, private val onItemClick: (id: Long) -> Unit) : RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder>() {

    var items = listOf<SearchSuggestion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_suggestion, parent, false))

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        glide.load(item.thumbnail).into(holder.thumbnail)

        holder.title.text = item.name
        holder.price.text = item.price

        holder.itemView.setOnClickListener {
            onItemClick(item.appId)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.searchSuggestionThumbnail)
        val title: TextView = itemView.findViewById(R.id.searchSuggestionTitle)
        val price: TextView = itemView.findViewById(R.id.searchSuggestionPrice)
    }
}