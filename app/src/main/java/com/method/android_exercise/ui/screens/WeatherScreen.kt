package com.method.android_exercise.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.method.android_exercise.R
import com.method.android_exercise.viewModel.WeatherViewModel

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = hiltViewModel()
) {

    val weather by weatherViewModel.weather.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> weatherViewModel.getRandomWeather()
                else -> return@LifecycleEventObserver
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(weather.weatherColor))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "${weather.temperature}" + if (weather.isCelsius) "°C" else "°F",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        Text(
            text = "${weather.weatherType}",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        OutlinedButton(
            onClick = { weatherViewModel.getRandomWeather() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(containerColor = Color.White, contentColor = Color.Blue, disabledContainerColor = Color.White, disabledContentColor = Color.Gray),
            border = BorderStroke(2.dp,Color.White),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Refresh",
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }

        OutlinedButton(
            onClick = { weatherViewModel.convertTemp(weather.isCelsius) },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonColors(containerColor = Color.White, contentColor = Color.Blue, disabledContainerColor = Color.White, disabledContentColor = Color.Gray),
            border = BorderStroke(2.dp,Color.White),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Show " + if (weather.isCelsius) "Fahrenheit" else "Celsius",
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }
}