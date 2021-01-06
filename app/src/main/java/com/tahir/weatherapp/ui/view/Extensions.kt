package com.tahir.weatherapp.ui.view

import android.content.res.Resources
import android.view.View
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun View.makeGone(): Boolean = visibility == View.GONE
fun View.makeVisible(): Boolean = visibility == View.VISIBLE
fun View.makeInvisible(): Boolean = visibility == View.INVISIBLE

fun Int.dpToPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return px.roundToInt()
}

