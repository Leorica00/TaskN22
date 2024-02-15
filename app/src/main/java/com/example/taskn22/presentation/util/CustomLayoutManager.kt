package com.example.taskn22.presentation.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class CustomLayoutManager(spanCount: Int, orientation: Int) :
    StaggeredGridLayoutManager(spanCount, orientation) {

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        val itemCount = state.itemCount

        if (itemCount == 1) {
            val width = View.MeasureSpec.getSize(widthSpec)
            val height = View.MeasureSpec.getSize(heightSpec)
            setMeasuredDimension(width, height)
        } else if (itemCount >= 2) {
            val totalWidth = View.MeasureSpec.getSize(widthSpec)
            val totalHeight = View.MeasureSpec.getSize(heightSpec)

            val itemWidth = totalWidth / 2
            val itemHeight = if (itemCount == 2) totalHeight else totalHeight / 2

            setMeasuredDimension(totalWidth, totalHeight)

            for (i in 0 until itemCount) {
                val child = recycler.getViewForPosition(i)
                measureChild(
                    child, View.MeasureSpec.makeMeasureSpec(itemWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(itemHeight, View.MeasureSpec.EXACTLY)
                )
            }
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec)
        }
    }
}