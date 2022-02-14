package com.babakmhz.nearbyscooters.view.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.babakmhz.nearbyscooters.appUtil.LOCATION_PERMISSION_REQUEST_CODE
import com.babakmhz.nearbyscooters.appUtil.toGone
import com.babakmhz.nearbyscooters.appUtil.toVisible
import com.google.android.material.snackbar.Snackbar

interface BaseViewHelper {


    fun <T : ViewModel> getSharedViewModel(activity: BaseActivity, viewModel: Class<T>): T {
        return ViewModelProvider(activity)[viewModel]
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestLocationPermission(activity: BaseActivity) {
        activity.requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    fun buildAlertMessageNoGps(activity: Activity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                activity.startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


    fun showErrorSnackBar(view: View) {
        Snackbar.make(view, "Error occurred!", Snackbar.LENGTH_LONG).setAction("OK") {
        }.show()
    }

    fun showSnackBar(
        view: View,
        message: String,
        actionText: String = "OK",
        onAction: () -> Unit = {}
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText) {
            onAction.invoke()
        }.show()
    }

    fun getProgressBar(): ProgressBar?

    fun showLoading() {
        getProgressBar()?.toVisible()
    }

    fun hideLoading() {
        getProgressBar()?.toGone()
    }

}