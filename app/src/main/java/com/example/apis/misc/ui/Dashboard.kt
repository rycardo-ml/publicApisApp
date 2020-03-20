package pt.sibs.android.mbway.mobileui.components.cardbuttonsmosaic

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.apis.R

open class Dashboard @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    private var minItemsSize: Int = 0
    private var maxItemsSize: Int = 0

    private val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        removeObserver()
        resizeChild()
    }

    init {
        fetchAttributes()
        viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun removeObserver() {
        viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (!isInEditMode) return
        resizeChild()
    }

    private fun resizeChild() {
        val itemSize = getItemsSize()

        children.iterator().forEach {
            val lp = (it.layoutParams as LayoutParams?) ?: LayoutParams(0, 0)

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

        var margin = 0
        children.iterator().forEach {
            margin += (it.layoutParams as LayoutParams).topMargin + it.paddingTop
            margin += (it.layoutParams as LayoutParams).bottomMargin + it.paddingBottom
        }

        return measuredHeight - padding - (margin / 3)
    }

    private fun getParentWidth(): Int {
        val padding = paddingLeft + paddingRight

        var margin = 0
        children.iterator().forEach {
            margin += (it.layoutParams as LayoutParams).leftMargin + it.paddingLeft
            margin += (it.layoutParams as LayoutParams).rightMargin + it.paddingRight
        }

        return measuredWidth - padding - (margin / 3)
    }

    private fun fetchAttributes() {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Dashboard, 0, 0)

        minItemsSize = typedArray.getDimension(R.styleable.Dashboard_minItemsSize, 0f).toInt()
        maxItemsSize = typedArray.getDimension(R.styleable.Dashboard_maxItemsSize, 0f).toInt()

        typedArray.recycle()
    }
}