/*
 * Copyright 2024 Sitharaj Seenivasan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sitharaj88.sensorwatcher.sample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sitharaj88.sensorwatcher.core.SensorError
import com.sitharaj88.sensorwatcher.SensorWatcher
import com.sitharaj88.sensorwatcher.sample.ui.theme.Android_sensor_watcherTheme
import com.sitharaj88.sensorwatcher.model.Magnetometer
import com.sitharaj88.sensorwatcher.sensor.MagnetometerSensor

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_sensor_watcherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    MySensorScreen(
                        context = this,
                        activity = this,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}


@Composable
fun MySensorScreen(context: Context, activity: Activity, paddingValues: PaddingValues) {
    var magnetometerSensorData by remember { mutableStateOf(Magnetometer(0f, y = 0f, z = 0f)) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val magnetometerSensor = MagnetometerSensor(context)

    SensorWatcher(
        context = context,
        activity = activity,
        sensorSpec = magnetometerSensor,
        onData = { data ->
            magnetometerSensorData = data
        },
        onError = { error ->
            errorMessage = when (error) {
                is SensorError.PermissionDenied -> "Permission Denied"
                is SensorError.SensorUnavailable -> "Sensor Unavailable"
                else -> "Unknown Error"
            }
        }
    )

    Column(modifier = Modifier.padding(paddingValues)) {
        BasicText(text = "X-Axis: ${magnetometerSensorData.x}")
        BasicText(text = "Y-Axis: ${magnetometerSensorData.y}")
        BasicText(text = "Z-Axis: ${magnetometerSensorData.z}")
        BasicText(text = "Sensor Name: ${magnetometerSensor.sensorInfo.name}")
        BasicText(text = "Vendor: ${magnetometerSensor.sensorInfo.vendor}")
        BasicText(text = "Version: ${magnetometerSensor.sensorInfo.version}")
        errorMessage?.let {
            BasicText(text = "Error: $it")
        }
    }
}
