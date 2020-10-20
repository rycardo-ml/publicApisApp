package com.example.apis.misc.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

class TouchConstraintLayout @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    init {
        setOnTouchListener { _, _ ->  performClick()}
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}