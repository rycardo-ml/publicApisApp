package com.example.apis.misc.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import com.example.apis.R

class Dashboard @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {

    private var minItemsSize: Int = 0
    private var maxItemsSize: Int = 0

    init {
        fetchAttributes()
        Log.d("melo", "init drawable with item ${childCount}")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("melo", "onMeasure drawable with item ${childCount}")

        resizeChild()
    }

    private fun resizeChild() {
        val itemSize = getItemsSize()

        children.iterator().forEach {
           val lp =  (it.layoutParams as LayoutParams?) ?: LayoutParams(0,0)

            lp.height = itemSize
            lp.width = itemSize

            it.layoutParams = lp
        }
    }

    private fun getItemsSize(): Int {
        val minValue = Math.min(getParentWidth(), getParentHeight())
        val itemSize: Int = (minValue / 3)

        if (maxItemsSize != 0 && itemSize > maxItemsSize) return maxItemsSize
        if (minItemsSize != 0 && itemSize < minItemsSize) return minItemsSize

        return itemSize
    }

    private fun getParentHeight(): Int {
        val padding = paddingTop + paddingBottom
        return measuredHeight - padding - getVerticalMargin()
    }

    private fun getParentWidth(): Int {
        val padding = paddingStart + paddingEnd
        return measuredWidth - padding - getHorizontalMargin()
    }

    private fun getHorizontalMargin(): Int {
        val lp = (layoutParams as LayoutParams?) ?: return 0

        return lp.rightMargin + lp.leftMargin
    }

    private fun getVerticalMargin(): Int {
        val lp = (layoutParams as LayoutParams?) ?: return 0

        return lp.topMargin + lp.bottomMargin
    }

    private fun fetchAttributes() {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Dashboard, 0, 0)

        minItemsSize = typedArray.getDimensionPixelSize(R.styleable.Dashboard_minItemsSize, 0)
        maxItemsSize = typedArray.getDimensionPixelSize(R.styleable.Dashboard_maxItemsSize, 0)

        typedArray.recycle()
    }
}
