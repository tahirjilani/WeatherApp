package com.tahir.weatherapp.utils

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val space: Int, private val isHorizontal: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if(isHorizontal) {
            outRect.left = space
            outRect.right = space
        }else{
            outRect.bottom = space
        }
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            if(isHorizontal) {
                outRect.left = space
            }else {
                outRect.top = space
            }
        } else {
            if(isHorizontal) {
                outRect.left = 0
            }else {
                outRect.top = 0
            }
        }
    }
}
