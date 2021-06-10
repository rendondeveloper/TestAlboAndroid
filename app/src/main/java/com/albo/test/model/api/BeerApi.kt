package com.albo.test.model.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class BeerApi(
    val id: Int,
    val description: String,
    val first_brewed: String,
    val food_pairing: List<String>,
    val image_url: String,
    val name: String,
    val tagline: String,
){
    val food_pairing_label : String
    get()  {
        val text = StringBuffer()
        food_pairing.forEach { item ->
            text.append("$item,")
        }
        return text.toString()
    }
}

