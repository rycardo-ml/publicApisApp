package com.example.apis.misc.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.widget.TextViewCompat
import com.example.apis.R


class DashboardItem @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attrs, defStyle) {

    private lateinit var title: AppCompatTextView
    private lateinit var image: AppCompatImageView

    private lateinit var titleText: String
    private var imageResource: Int = 0

    init {
        fetchAttributes()

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
        image.layoutParams = LayoutParams(0, 0).apply {
            topMargin = 10
        }
        image.setImageResource(imageResource)

        addView(image)
        configureImageConstraints()
    }

    private fun setupTitle() {
        title = AppCompatTextView(context)
        title.id = View.generateViewId()
        title.layoutParams = LayoutParams(0,0).apply {
            bottomMargin = 10
            leftMargin = 10
            rightMargin = 10
        }
        title.gravity = Gravity.CENTER
        title.textSize = resources.getDimension(R.dimen.txt_normal)
        title.text = titleText
        TextViewCompat.setAutoSizeTextTypeWithDefaults(title, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

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

    private fun fetchAttributes() {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DashboardItem, 0, 0)

        titleText = typedArray.getStringOrThrow(R.styleable.DashboardItem_title)
        imageResource = typedArray.getResourceIdOrThrow(R.styleable.DashboardItem_image)

        typedArray.recycle()
    }
}
