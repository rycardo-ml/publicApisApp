package com.example.apis.misc.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.UnsupportedOperationException

// https://medium.com/@supahsoftware/custom-android-views-carousel-recyclerview-7b9318d23e9a

private const val MINIMUN_SIZE = .4f
class CarouselRecyclerView@JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0): RecyclerView(context, attrs, defStyle) {

    private var initialized = false
    private var viewCenter: Float = 0f
//    private var offsetCenter: Float = 0f
    private var limitLeft: Float = 0f
    private var limitRight: Float = 0f

    private var initialPositionX: Float = 0f
    private var actualPositionX: Float = 0f

    init {
        super.setLayoutManager(LinearLayoutManager(context, HORIZONTAL, false))
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        throw UnsupportedOperationException("Please don't change the layout manager for CarouselRecyclerView")
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        initialPositionX = oldl.toFloat()
        actualPositionX = l.toFloat()

        updateChildrenLayout()
    }

//    fun <T : ViewHolder> initialize(newAdapter: Adapter<T>) {
//
//
////        newAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
////            override fun onChanged() {
////                post {
////                    //val sidePadding = (width / 2) - (getChildAt(0).width / 2)
//////                    setPadding(32.toPx, 16.toPx, 32.toPx, 16.toPx)
////                    //setPadding(sidePadding, 0, sidePadding, 0)
////
//////                    scrollToPosition(0)
//////                    addOnScrollListener(object : OnScrollListener() {
//////                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//////                            super.onScrolled(recyclerView, dx, dy)
//////                            onScrollChanged()
//////                        }
//////                    })
////                }
////            }
////        })
//        adapter = newAdapter
//    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (initialized) return

        initialized = true
        viewCenter = (left + right).toFloat() / 2
//        offsetCenter = (right * .15f)
        limitLeft = (right * .05f)
        limitRight = right - limitLeft

        Log.d("melo", "viewCenter $viewCenter")
//        Log.d("melo", "offsetCenter $offsetCenter")
        Log.d("melo", "limitLeft $limitLeft")
        Log.d("melo", "limitRight $limitRight")
    }

    private fun updateChildrenLayout() {
        post {
            (0 until childCount).forEach {position ->


                val child = getChildAt(position)
                val childCenter = (child.left + child.right) / 2

                if (position == 1) {
                    Log.d("melo", "________________________position $position")
                    Log.d("melo", "childCenter $childCenter")
                }

                val scale = getNewScale(childCenter)

                if (position == 1) {
                    Log.d("melo", "scale $scale")
                }

                child.scaleX = scale
                child.scaleY = scale
            }
        }
    }

    private fun getNewScale(childCenter: Int): Float {

        if (childCenter < viewCenter) {
            return getNewScaleLeft(childCenter)
        }

        if (childCenter > viewCenter) {
            return getNewScaleRight(childCenter)
        }

        return 1f
    }

    private fun getNewScaleRight(childCenter: Int): Float {

//        val limit = viewCenter - offsetCenter
        val limit = viewCenter

        if (childCenter < limit) return 1f
        if (childCenter >= limitRight) return MINIMUN_SIZE

        val childAbs = childCenter - (viewCenter - limitLeft)
        val limitAbs = limit - limitLeft

        if (childAbs <= 0) return MINIMUN_SIZE

        val percentage: Float = (childAbs / limitAbs)

        return ((MINIMUN_SIZE * percentage) + MINIMUN_SIZE)
    }

    private fun getNewScaleLeft(childCenter: Int): Float {

//        val limit = viewCenter - offsetCenter
        val limit = viewCenter

        if (childCenter > limit) return 1f
        if (childCenter <= limitLeft) return MINIMUN_SIZE

        val childAbs = childCenter - limitLeft
        val limitAbs = limit - limitLeft

        if (childAbs <= 0) return MINIMUN_SIZE

        val percentage: Float = (childAbs / limitAbs)

        return ((MINIMUN_SIZE * percentage) + MINIMUN_SIZE)
    }


//    private fun onScrollChanged() {
//
//        Log.d("melo", "------------------onScrollChanged----------------------")
//
//        if (!initialized) return
//
//        post {
//            (0 until childCount).forEach { position ->
//                Log.d("melo", "________________________position $position")
//
//                val child = getChildAt(position)
//                val childCenterX = (child.left + child.right) / 2
//
////                val scaleValue = getGaussianScale(childCenterX, 1f, 1f, 100.toDouble())
//                getScale(child)
//
//                //Log.d("melo", "scaleValue $scaleValue")
//
////                child.scaleX = scaleValue
////                child.scaleY = scaleValue
////                colorView(child, scaleValue)
//            }
//        }
//    }

//    private fun getScale(child: View) {
//
//        val childCenter = (child.left + child.right) / 2
//
//        //Log.d("melo", "child.left ${child.left}")
//        //Log.d("melo", "child.right ${child.right}")
//        Log.d("melo", "childCenter $childCenter")
//        //child.x = 0f
//
////        if (!shouldRescaleMovingLeft(childCenter)) {
////            child.scaleX = 1f
////            child.scaleY = 1f
////
////            return
////        }
//
//
//
//
//////        layoutParams.setMargins(0.toPx, 0.toPx, 0.toPx, 0.toPx)
////        if (childCenter > right) {
////            child.scaleX = .5f
////            child.scaleY = .5f
////
////            //if (child.left < limitLeft) child.x = limitLeft.toFloat()
////            //if (child.right > limitRight) child.x = limitRight.toFloat()
////            //child.x = limitRight.toFloat()
////
////
////            return
////        }
////
////        if (childCenter < left) {
////            child.scaleX = .5f
////            child.scaleY = .5f
////
//////            if (child.left < limitLeft) child.x = limitLeft.toFloat()
//////            if (child.right > limitRight) child.x = limitRight.toFloat()
////
////            return
////        }
//
////        layoutParams.setMargins(32.toPx, 32.toPx, 32.toPx, 32.toPx)
//
//
//            child.scaleX = 1f
//            child.scaleY = 1f
//
//        /*
//        ---------------------|---------------------
//         */
//    }

//    private fun shouldRescaleMovingLeft(childCenter: Int): Boolean{
//        if (!isMovingLeft()) return false
//
//        if (childCenter < viewCenter)
//            return childCenter < (viewCenter - offsetCenter)
//
//        if (childCenter > viewCenter) {
//            return childCenter > (viewCenter + offsetCenter)
//        }
//
//        return false
//    }

    private fun isMovingLeft(): Boolean = actualPositionX <= initialPositionX
    private fun isMovingRight(): Boolean = actualPositionX > initialPositionX

//    private fun getGaussianScale(childCenterX: Int, minScaleOffest: Float, scaleFactor: Float, spreadFactor: Double): Float {
//        val recyclerCenterX = (left + right) / 2
//
//        return (
//                Math.pow(
//                    Math.E,
//                    -Math.pow(
//                        childCenterX - recyclerCenterX.toDouble(),
//                        2.toDouble()
//                    ) / (2 * Math.pow(spreadFactor, 2.toDouble()))
//                ) * scaleFactor + minScaleOffest).toFloat()
//    }

//    private fun colorView(child: View, scaleValue: Float) {
//        val saturationPercent = (scaleValue - 1) / 1f
//        val alphaPercent = scaleValue / 2f
//        val matrix = ColorMatrix()
//        matrix.setSaturation(saturationPercent)
//
//        viewsToChangeColor.forEach { viewId ->
//            val viewToChangeColor = child.findViewById<View>(viewId)
//
//            when (viewToChangeColor) {
//                is ImageView -> {
//                    viewToChangeColor.colorFilter = ColorMatrixColorFilter(matrix)
//                    viewToChangeColor.imageAlpha = (255 * alphaPercent).toInt()
//                }
//
//                is TextView -> {
//                    val textColor = ArgbEvaluator().evaluate(saturationPercent, inactiveColor, activeColor) as Int
//                    viewToChangeColor.setTextColor(textColor)
//                }
//            }
//        }
//    }
//
//    fun setViewsToChangeColor(viewIds: List<Int>) {
//        viewsToChangeColor = viewIds
//    }
}