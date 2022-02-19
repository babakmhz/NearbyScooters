package com.babakmhz.nearbyscooters.appUtil

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.babakmhz.nearbyscooters.R
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
    @SuppressLint("SetTextI18n")
    @BindingAdapter("bindDistance")
    @JvmStatic
    fun bindDistanceToTextView(view:TextView,value:Int){
        val suffix = if (value>=1000) "Km" else "M"
        val isKm = value>=1000
        val newValue =  if(isKm)value/1000F else value
        view.text = String.format(view.context.getString(R.string.distance_s),"$newValue$suffix")
    }
}