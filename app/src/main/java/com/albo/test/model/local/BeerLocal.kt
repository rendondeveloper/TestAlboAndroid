package com.albo.test.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "BeerLocal")
data class BeerLocal(
    @PrimaryKey @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "first_brewed")
    val first_brewed: String,
    @ColumnInfo(name = "food_pairing")
    val food_pairing: String,
    @ColumnInfo(name = "image_url")
    val image_url: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "tagline")
    val tagline: String,
    @ColumnInfo(name = "page")
    val page : Int,
)

