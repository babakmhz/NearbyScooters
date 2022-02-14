package com.babakmhz.nearbyscooters.view.base

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), BaseViewHelper {


    override fun onStart() {
        super.onStart()
        initializeUi()
    }

    override fun getProgressBar(): ProgressBar? {
        return null
    }

    abstract fun initializeUi()


}