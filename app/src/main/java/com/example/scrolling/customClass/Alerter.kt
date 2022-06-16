package com.example.scrolling.customClass


import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.blankj.utilcode.util.ActivityUtils
import com.example.scrolling.R
import com.irozon.sneaker.Sneaker

class Alerter(title: String?, message: String, duration: Int = 4000) {



    init {
        val activity = ActivityUtils.getTopActivity()
        Sneaker.with(activity) // Activity, Fragment or ViewGroup
                .setTitle(title ?: "Success", R.color.white) // Title and title color
                .setMessage(message, R.color.white) // Message and message color
                .setDuration(duration) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setCornerRadius(9)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                .setTypeface(
                        ResourcesCompat.getFont(
                                activity,
                                R.font.iransansmobile
                        )!!
                )  // Custom font for title and message
                .autoHide(true)
                .sneak(R.color.colorAccent) // Sneak with background color


    }

}

