package com.github.lzaytseva.util

sealed class Resource<T>(val data: T? = null, val error: String? = null) {

    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(error: String) : Resource<T>(error = error)
}
