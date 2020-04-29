package com.example.arman

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class LockableViewPager : ViewPager {
    var isSwipable = false

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        isSwipable = true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (isSwipable) {
            super.onInterceptTouchEvent(ev)
        } else false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isSwipable) {
            super.onTouchEvent(ev)
        } else false
    }
}
