package com.babakmhz.nearbyscooters.appUtil

sealed class MainUiState<out T : Any>{
    object Loading : MainUiState<Nothing>()
    data class Error(val error: Throwable?=null) : MainUiState<Nothing>()
    data class Success<out T : Any>(val data: T) : MainUiState<T>()
}