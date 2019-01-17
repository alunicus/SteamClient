package com.github.alunicus.steamclient.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.github.alunicus.steamclient.R
import com.github.alunicus.steamclient.util.toggleRotation180
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.view_expansion_text_card.view.*

class ExpansionTextCard : MaterialCardView {

    var shortText: CharSequence = ""
    var fullText: CharSequence = ""

    companion object {
        const val SHORT_TEXT_LIMIT = 300
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val inflater = LayoutInflater.from(context)

        inflater.inflate(R.layout.view_expansion_text_card, this)

        textCardExpandButton.setOnClickListener {
            val show = it.toggleRotation180()

            textCardText.text = if (show) fullText else shortText
        }
    }

    fun setTitle(title: String) {
        textCardTitle.text = title
    }

    fun setText(text: CharSequence) {
        fullText = text

        val endIndex = if (text.length > SHORT_TEXT_LIMIT) SHORT_TEXT_LIMIT else text.length

        shortText = "${text.substring(0, endIndex)} ..."

        textCardText.text = shortText
    }
}