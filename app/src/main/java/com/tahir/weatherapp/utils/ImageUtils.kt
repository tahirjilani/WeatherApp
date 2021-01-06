package com.tahir.weatherapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tahir.weatherapp.BuildConfig
import com.tahir.weatherapp.R

object ImageUtils {

    fun load(context: Context, imageView: ImageView, name: String){

        val url = BuildConfig.IMAGES_URL + "$name.png"
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_broken_image_24)
            .into(imageView)
    }

    fun load2X(context: Context, imageView: ImageView, name: String){
        load(context, imageView, "$name@2x.png")
    }
}