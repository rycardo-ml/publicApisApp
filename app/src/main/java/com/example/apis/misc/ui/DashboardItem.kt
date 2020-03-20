package pt.sibs.android.mbway.mobileui.components.cardbuttonsmosaic

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.widget.TextViewCompat
import com.example.apis.R

class DashboardItem @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    private lateinit var title: AppCompatTextView
    private lateinit var image: AppCompatImageView

    private lateinit var titleText: String
    private var imageResource: Int = 0

    init {
        fetchAttributes()

        setupViews()
        setupConstraintsBetweenViews()

        setPadding(
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics).toInt(),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics).toInt(),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics).toInt(),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics).toInt()
        )
    }

    private fun setupViews() {
        setupImage()
        setupTitle()
    }

    private fun setupImage() {
        image = AppCompatImageView(context)
        image.id = View.generateViewId()
        image.setImageResource(imageResource)
        image.layoutParams = LayoutParams(0, 0)

        addView(image)
        configureImageConstraints()
    }

    private fun setupTitle() {
        title = AppCompatTextView(context)
        title.id = View.generateViewId()
        title.layoutParams = LayoutParams(0, 0).apply {
            topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics).toInt()
        }

        title.gravity = Gravity.CENTER
        title.text = titleText

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
            title,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11f, resources.displayMetrics).toInt(),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14f, resources.displayMetrics).toInt(),
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics).toInt(),
            TypedValue.COMPLEX_UNIT_PX
        )

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