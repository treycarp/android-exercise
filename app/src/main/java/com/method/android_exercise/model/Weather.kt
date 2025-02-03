package com.method.android_exercise.model

import android.os.Parcelable
import com.method.android_exercise.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class Weather(
    val temperature: Int? = null,
    val weatherType: WeatherType? = null,
    val weatherColor: Int = R.color.purple_200,
    val isCelsius: Boolean = false
) : Parcelable
