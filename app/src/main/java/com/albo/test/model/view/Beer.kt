package com.albo.test.model.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    val id : Int,
    val description: String,
    val firstBrewed: String,
    val foodPairing: String,
    val imageUrl: String,
    val name: String,
    val tagline: String,
) : Parcelable

