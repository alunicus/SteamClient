package com.github.alunicus.steamclient.data.source.remote.model

data class VideoResponse(
        val id: Int,
        val name: String,
        val thumbnail: String,
        val webm: VideoUrl,
        val highlight: Boolean
)