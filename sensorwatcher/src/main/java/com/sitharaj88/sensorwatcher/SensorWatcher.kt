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

package com.sitharaj88.sensorwatcher

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.sitharaj88.sensorwatcher.core.PermissionManager
import com.sitharaj88.sensorwatcher.core.SensorError
import com.sitharaj88.sensorwatcher.core.SensorSpec
import kotlinx.coroutines.launch

@Composable
fun <T> SensorWatcher(
    context: Context,
    activity: Activity,
    sensorSpec: SensorSpec<T>,
    onData: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    val permissionManager = remember { PermissionManager(context) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val baseSensor = sensorSpec.createSensor(context)
        val sensorJob = coroutineScope.launch {
            val permission = sensorSpec.requiredPermission
            if (permission == null || permissionManager.isPermissionGranted(
                    permission
                )) {
                try {
                    baseSensor.start().collect { data ->
                        onData(data)
                    }
                } catch (e: Exception) {
                    onError(e)
                }
            } else {
                onError(SensorError.PermissionDenied)
                permissionManager.requestPermission(activity, permission, 1001)
            }
        }

        // Stop the sensor when the composable is disposed
        onDispose {
            sensorJob.cancel()
            baseSensor.stop() // Unregister the sensor listener
        }
    }
}
