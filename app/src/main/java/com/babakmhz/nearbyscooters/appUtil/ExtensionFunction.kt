package com.babakmhz.nearbyscooters.appUtil

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun View?.toVisible() {
    if (this?.visibility != View.VISIBLE)
        this?.visibility = View.VISIBLE
}

fun View?.toGone() {
    if (this?.visibility != View.GONE)
        this?.visibility = View.GONE
}

fun View?.toInvisible() {
    if (this?.visibility != View.INVISIBLE)
        this?.visibility = View.INVISIBLE
}


fun String?.validString() = this != null && this.isNotEmpty()

fun Fragment.isNavGraphStartingPoint(navController: NavController) =
    navController.graph.startDestinationId == navController.currentDestination?.id ?: -1

fun <T : Any> CoroutineScope.launchWithException(
    livedata: MutableLiveData<MainUiState<T>>,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(CoroutineExceptionHandler { _, throwable ->
        livedata.postValue(MainUiState.Error(throwable))
    }, block = block)
}


