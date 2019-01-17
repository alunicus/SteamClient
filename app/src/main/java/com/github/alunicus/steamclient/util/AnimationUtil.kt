package com.github.alunicus.steamclient.util

import android.view.View

const val ROTATION_DURATION = 200L

fun View.toggleRotation180() = this.toggleRotation(180f)

private fun View.toggleRotation(rotation: Float): Boolean {
    return if (this.rotation == 0f) {
        animate().setDuration(ROTATION_DURATION).rotation(rotation)
        true
    } else {
        animate().setDuration(ROTATION_DURATION).rotation(0f)
        false
    }
}