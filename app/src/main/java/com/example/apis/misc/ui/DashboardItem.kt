package com.example.apis.misc.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.*
import com.example.apis.R


class DashboardItem @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {

    private lateinit var title: AppCompatTextView
    private lateinit var image: AppCompatImageView

    init {
        setupViews()
        setupConstraintsBetweenViews()
    }

    private fun setupViews() {
        setupImage()
        setupTitle()
     }

    private fun setupImage() {
        image = AppCompatImageView(context)
        image.id = View.generateViewId()
//        image.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        image.layoutParams = LayoutParams(0, 0)

        image.setImageResource(R.drawable.ic_envelope)

        addView(image)
        configureImageConstraints()
    }

    private fun setupTitle() {
        title = AppCompatTextView(context)
        title.id = View.generateViewId()
        title.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        title.gravity = Gravity.CENTER

        title.text = "Option text"

        addView(title)
        configureTitleConstraints()
    }

    private fun configureImageConstraints() {
        val set = ConstraintSet()
        set.clone(this)

        set.connect(image.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

        set.connect(image.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        set.connect(image.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

        set.connect(image.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        set.connect(image.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        set.applyTo(this)
    }

    private fun configureTitleConstraints() {
        val set = ConstraintSet()
        set.clone(this)

        set.connect(title.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

        set.connect(title.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        set.connect(title.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

        set.connect(title.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        set.connect(title.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        set.applyTo(this)
    }

    private fun setupConstraintsBetweenViews() {
        val set = ConstraintSet()
        set.clone(this)

        set.connect(title.id, ConstraintSet.TOP, image.id, ConstraintSet.BOTTOM)

        set.connect(image.id, ConstraintSet.BOTTOM, title.id, ConstraintSet.TOP)

        set.applyTo(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minValue = Math.min(getParentWidth(), getParentHeight())
        val itemSize: Int = minValue / 3

        val maxSize = Math.min(maxWidth, maxHeight)
        val minSize = Math.min(minWidth, minHeight)

        if (itemSize > maxSize) {
            setMeasuredDimension(maxSize, maxSize)
            return
        }

        if (itemSize < minSize) {
            setMeasuredDimension(minSize, minSize)
            return
        }

        setMeasuredDimension(itemSize, itemSize)
    }

    private fun getParentHeight(): Int {
        val parentView = (parent as View)

        val padding = parentView.paddingTop + parentView.paddingBottom
        val margin = parentView.marginTop + parentView.marginBottom

        return parentView.measuredHeight - padding - margin
    }

    private fun getParentWidth(): Int {
        val parentView = (parent as View)

        val padding = parentView.paddingStart + parentView.paddingEnd
        val margin = parentView.marginLeft + parentView.marginRight

        return parentView.measuredWidth - padding - margin
    }
}
