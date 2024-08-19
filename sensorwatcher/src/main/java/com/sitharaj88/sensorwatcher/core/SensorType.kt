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

import android.Manifest
import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi

enum class SensorType(val sensor: Int,  val requiredPermission: String?) {
    ACCELEROMETER(Sensor.TYPE_ACCELEROMETER, Manifest.permission.BODY_SENSORS),
    GYROSCOPE(Sensor.TYPE_GYROSCOPE, Manifest.permission.BODY_SENSORS),
    LIGHT(Sensor.TYPE_LIGHT, null),
    PROXIMITY(Sensor.TYPE_PROXIMITY, null),
    MAGNETOMETER(Sensor.TYPE_MAGNETIC_FIELD, null),
    GRAVITY(Sensor.TYPE_GRAVITY, null),
    LINEAR_ACCELERATION(Sensor.TYPE_LINEAR_ACCELERATION, null),
    ROTATION_VECTOR(Sensor.TYPE_ROTATION_VECTOR, null),
    TEMPERATURE(Sensor.TYPE_AMBIENT_TEMPERATURE, null),
    PRESSURE(Sensor.TYPE_PRESSURE, null),
    RELATIVE_HUMIDITY(Sensor.TYPE_RELATIVE_HUMIDITY, null),
    HEART_RATE(Sensor.TYPE_HEART_RATE,Manifest.permission.BODY_SENSORS),
    @RequiresApi(Build.VERSION_CODES.Q)
    STEP_COUNTER(Sensor.TYPE_STEP_COUNTER, Manifest.permission.ACTIVITY_RECOGNITION),
    @RequiresApi(Build.VERSION_CODES.Q)
    STEP_DETECTOR(Sensor.TYPE_STEP_DETECTOR, Manifest.permission.ACTIVITY_RECOGNITION)
}
