package com.albo.test.utils

import android.R
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.bindImageUrl(
    url: String?,
    @DrawableRes errorPlaceholder: Int,
    @DrawableRes loader: Int
) {
    if (url.isNullOrBlank()) {
        setImageResource(errorPlaceholder)
        return
    }

    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(loader)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()

    Glide.with(context)
        .load(url)
        .fitCenter()
        .error(errorPlaceholder)
        .apply(options)
        .into(this)
}