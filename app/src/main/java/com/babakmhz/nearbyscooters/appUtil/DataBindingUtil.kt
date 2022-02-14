package com.babakmhz.nearbyscooters.appUtil

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import eo.view.batterymeter.BatteryMeterView


object CustomBindingAdapter{
    @BindingAdapter("batteryPercentage")
    @JvmStatic
    fun batteryPercentage(view:BatteryMeterView,value:Int){
        view.chargeLevel = value
    }
    @SuppressLint("SetTextI18n")
    @BindingAdapter("batteryLevelText")
    @JvmStatic
    fun batteryPercentage(view:TextView,value:Int){
        view.text = "$value%"
    }
}