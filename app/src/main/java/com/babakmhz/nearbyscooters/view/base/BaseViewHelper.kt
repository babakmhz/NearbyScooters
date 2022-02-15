package com.babakmhz.nearbyscooters.view.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.babakmhz.nearbyscooters.R
import com.babakmhz.nearbyscooters.appUtil.LOCATION_PERMISSION_REQUEST_CODE
import com.babakmhz.nearbyscooters.appUtil.toGone
import com.babakmhz.nearbyscooters.appUtil.toVisible
import com.google.android.material.snackbar.Snackbar

interface BaseViewHelper {


    fun <T : ViewModel> getSharedViewModel(activity: BaseActivity, viewModel: Class<T>): T {
        return ViewModelProvider(activity)[viewModel]
    }

    fun requestLocationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    fun requestLocationPermission(fragment: Fragment) {
        fragment.requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    fun showLocationProviderDisabledAlert(activity: Activity) {
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


    fun showErrorSnackBar(context: Context, view: View, throwable: Throwable) {
        Snackbar.make(
            view,
            context.getString(R.string.error_user_location, throwable.toString()),
            Snackbar.LENGTH_LONG
        ).setAction("OK") {
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