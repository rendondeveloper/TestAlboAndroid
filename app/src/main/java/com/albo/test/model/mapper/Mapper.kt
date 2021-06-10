package com.albo.test.model.mapper

import com.albo.test.model.view.Beer
import com.albo.test.model.api.BeerApi
import com.albo.test.model.local.BeerLocal

fun BeerApi.toBeerLocal(page: Int) = BeerLocal(
    id,
    description,
    first_brewed,
    food_pairing_label,
    image_url,
    name,
    tagline,
    page = page
)

fun BeerApi.toBeerView() = Beer(
    id,
    description,
    first_brewed,
    food_pairing_label,
    image_url,
    name,
    tagline
)

fun BeerLocal.toBeerView() = Beer(
    id,
    description,
    first_brewed,
    food_pairing,
    image_url,
    name,
    tagline
)

fun List<BeerApi>.toListBeerView() : List<Beer>{
    val list: MutableList<Beer> = mutableListOf()
    this.forEach { item -> list.add(item.toBeerView())}
    return list
}

fun List<BeerApi>.toListBeerLocal(per_page : Int) : List<BeerLocal>{
    val list: MutableList<BeerLocal> = mutableListOf()
    this.forEach { item -> list.add(item.toBeerLocal(per_page))}
    return list
}

fun List<BeerLocal>.toListBeerViewTo() : List<Beer>{
    val list: MutableList<Beer> = mutableListOf()
    this.forEach { item -> list.add(item.toBeerView())}
    return list
}

