package com.method.android_exercise.viewModel

import androidx.core.graphics.red
import androidx.lifecycle.ViewModel
import com.method.android_exercise.R
import com.method.android_exercise.model.Weather
import com.method.android_exercise.model.WeatherType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor() : ViewModel() {

    private val _weather: MutableStateFlow<Weather> = MutableStateFlow(Weather())
    val weather: StateFlow<Weather> = _weather

    fun getRandomWeather() {
        val fahrenheitTemperature = (-10..112).random()
        val weatherType = WeatherType.entries.toTypedArray().random()
        val weatherColor = getWeatherColor(weatherType)
        _weather.update {
            it.copy(
                temperature = fahrenheitTemperature,
                weatherType = weatherType,
                weatherColor = weatherColor
            )
        }
    }

    fun getWeatherColor(weatherType: WeatherType): Int {
        return when (weatherType) {
            WeatherType.SUNNY -> R.color.yellow
            WeatherType.SNOW,
            WeatherType.CLOUDY,
            WeatherType.RAIN,
            WeatherType.PARTLY_CLOUDY -> R.color.gray
            else -> R.color.red
        }
    }

    fun convertTemp(isCelsius: Boolean) {
        if (isCelsius) {
            _weather.update {
                it.copy(
                    temperature = ((_weather.value.temperature?.times(9))?.div(5))?.plus(32),
                    isCelsius = false
                )
            }
        } else {
            _weather.update {
                it.copy(
                    temperature = (((_weather.value.temperature?.minus(32))?.times(5))?.div(9)),
                    isCelsius = true
                )
            }
        }
    }
}