package com.method.android_exercise.ui

sealed class NavigationItem(var route: String, var title: String) {
    data object RandomNumberScreen : NavigationItem("randomNumberScreen", "Random Number")
    data object WeatherScreen : NavigationItem("weatherScreen", "Weather")
}