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

package com.sitharaj88.sensorwatcher.core

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class BaseSensor<T>(
    private val context: Context,
    private val sensorSpec: SensorSpec<T>,
    private val sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL
) : SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(sensorSpec.sensorType)
    private val sensorDataFlow = MutableSharedFlow<T>(replay = 1, extraBufferCapacity = 1)

    fun start(): Flow<T> {
        sensor?.let {
            sensorManager.registerListener(this, it, sensorDelay)
        } ?: throw SensorError.SensorUnavailable
        return sensorDataFlow
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val data = sensorSpec.processSensorData(event.values)
        val emitted = sensorDataFlow.tryEmit(data)
        if (!emitted) {
            // Handle emission failure if necessary
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if necessary
    }
}
