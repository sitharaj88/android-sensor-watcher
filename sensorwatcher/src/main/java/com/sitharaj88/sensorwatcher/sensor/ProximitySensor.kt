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

package com.sitharaj88.sensorwatcher.sensor

import android.content.Context
import android.hardware.SensorManager
import com.sitharaj88.sensorwatcher.core.BaseSensor
import com.sitharaj88.sensorwatcher.core.SensorInfo
import com.sitharaj88.sensorwatcher.core.SensorSpec
import com.sitharaj88.sensorwatcher.core.SensorType
import com.sitharaj88.sensorwatcher.model.ProximitySensor

class ProximitySensor(context: Context) : SensorSpec<ProximitySensor> {
    override val sensorType: Int = SensorType.PROXIMITY.sensor
    override val requiredPermission: String? = SensorType.PROXIMITY.requiredPermission

    override val sensorInfo: SensorInfo by lazy {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(sensorType)
        SensorInfo(
            name = sensor?.name ?: "Proximity",
            vendor = sensor?.vendor ?: "Unknown",
            version = sensor?.version ?: -1,
            type = sensor?.type ?: -1,
            maxRange = sensor?.maximumRange ?: -1.0f,
            resolution = sensor?.resolution ?: -1.0f,
            power = sensor?.power ?: -1.0f,
        )
    }


    override fun processSensorData(values: FloatArray): ProximitySensor {
        return ProximitySensor(values[0])
    }

    override fun createSensor(context: Context): BaseSensor<ProximitySensor> {
        return BaseSensor(context, this)
    }
}
