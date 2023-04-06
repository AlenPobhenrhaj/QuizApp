package com.example.quizapp.utils

sealed class Resource<T>(
    val resourceData: T? = null,
    val resourceMessage: String? = null
) {
    class Success<T>(data: T) : Resource<T>(resourceData = data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(resourceData = data, resourceMessage = message)
    class Loading<T> : Resource<T>()
}
