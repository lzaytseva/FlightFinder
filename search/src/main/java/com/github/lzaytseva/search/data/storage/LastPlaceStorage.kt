package com.github.lzaytseva.search.data.storage

internal interface LastPlaceStorage {

    fun get(): String?

    fun save(place: String)
}