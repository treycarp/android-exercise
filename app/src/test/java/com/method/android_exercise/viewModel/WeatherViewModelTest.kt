package com.method.android_exercise.viewModel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.method.android_exercise.model.Weather
import com.method.android_exercise.model.WeatherType
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var mockWeather: Weather


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        weatherViewModel = WeatherViewModel()
        mockWeather = Weather(
            temperature = 32,
            weatherType = WeatherType.SUNNY,
            isCelsius = false
        )
    }

    @Test
    fun getRandomWeather() {
        weatherViewModel.getRandomWeather()
        assertThat(weatherViewModel.weather.value.temperature).isIn(-10..112)
    }

    @Test
    fun convertTemp() {
        weatherViewModel.convertTemp(false)
        assertThat(weatherViewModel.weather.value.isCelsius).isTrue()
    }
}
