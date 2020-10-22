package com.example.apis.main.home.ui.adapter

import android.graphics.drawable.TransitionDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.apis.R
import com.example.apis.misc.models.Category

class CategoriesAdapter(val clickCallback: (Int) -> Unit): RecyclerView.Adapter<CategoriesAdapter.CategoryHolder>() {

    private val items = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return CategoryHolder(row)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<Category>) {
        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    inner class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val transition = ResourcesCompat.getDrawable(itemView.resources, R.drawable.transition_category, itemView.context.theme) as TransitionDrawable
        private val tvDescription: TextView = itemView.findViewById(R.id.row_category_tv_description)

        private var item: Category? = null

        init {
            transition.isCrossFadeEnabled = true
            configureClicks()
        }

        fun bind(item: Category) {
            this.item = item

            tvDescription.text = item.type.description
            updateBackground(item.selected, true)
        }

        private fun updateBackground(selected: Boolean, fixed: Boolean) {
            if (selected && fixed) {
                itemView.setBackgroundResource(R.drawable.shape_category_selected)
                return
            }

            if (selected) {
                itemView.background = transition
                transition.startTransition(750)
                return
            }

            transition.startTransition(0)
            transition.reverseTransition(0)
            itemView.setBackgroundResource(R.drawable.shape_category_normal)
        }

        private fun configureClicks() {
            itemView.setOnTouchListener { v, event ->
                when (event.action) {

                    MotionEvent.ACTION_DOWN -> {
                        updateBackground(true, false)
                        return@setOnTouchListener false
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        updateBackground(false, false)
                        return@setOnTouchListener false
                    }

                    MotionEvent.ACTION_MOVE -> {
                        return@setOnTouchListener false
                    }

                    MotionEvent.ACTION_UP -> {
                        return@setOnTouchListener itemView.performClick()
                    }
                }

                false
            }

            itemView.setOnClickListener {
                clickCallback.invoke(adapterPosition)
            }
        }
    }
}