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

interface SensorSpec<T> {
    val sensorType: Int
    val requiredPermission: String?
    val sensorInfo: SensorInfo

    fun processSensorData(values: FloatArray): T

    // New method to create the BaseSensor instance
    fun createSensor(context: Context): BaseSensor<T>
}


data class SensorInfo(
    val name: String,
    val vendor: String,
    val version: Int,
    val type: Int,
    val maxRange: Float,
    val resolution: Float,
    val power: Float
)
