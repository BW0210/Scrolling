package com.example.scrolling.customClass

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface ClickListener {
    fun onClick(view: View?, position: Int)
    fun onLongClick(view: View?, position: Int)
}



open class CustomRecyclerViewTouchListener(
        context: Context?,
        recycleView: RecyclerView,
        val clicklistener: ClickListener?
) :
        RecyclerView.OnItemTouchListener {
    private val gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val child = recycleView.findChildViewUnder(e.x, e.y)
            if (child != null && clicklistener != null) {
                clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
            }
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
            clicklistener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}