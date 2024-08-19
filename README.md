
# Android Sensor Watcher

[![](https://jitpack.io/v/sitharaj88/android-sensor-watcher.svg)](https://jitpack.io/#sitharaj88/android-sensor-watcher)

A flexible and reusable Android library for managing various sensors within a Jetpack Compose UI. The `android-sensor-watcher` library provides an easy-to-use API for integrating sensor data into your applications, complete with lifecycle management, error handling, and sensor information display.

## Features

- **Sensor Management:** Easily manage sensors such as accelerometer, gyroscope, temperature, and more.
- **Lifecycle-Aware:** Automatically start and stop sensors based on the lifecycle of your Jetpack Compose components.
- **Error Handling:** Built-in support for handling sensor-related errors like permission denial and sensor unavailability.
- **Flexible Architecture:** Extensible design using `SensorSpec` and `BaseSensor`, allowing for the addition of new sensors with minimal effort.

## Installation

To add the `android-sensor-watcher` library to your project, include it in your `build.gradle`:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```gradle
dependencies {
    implementation 'com.github.sitharaj88:android-sensor-watcher:1.0.0'
}
```

## Usage

### 1. Initialize a SensorSpec

Create an instance of a specific sensor spec, for example, the `TemperatureSpec`:

```kotlin
val temperatureSpec = TemperatureSpec(context)
```

### 2. Use SensorWatcher in Composable

Use the `SensorWatcher` composable to observe and handle sensor data:

```kotlin
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
```

### 3. Add Permissions

If a sensor requires specific permissions, ensure they are declared in your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.BODY_SENSORS" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```

### 4. Stop the Sensor

The library automatically handles starting and stopping the sensor based on the lifecycle of the composable, ensuring efficient resource management.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Author

**Sitharaj Seenivasan**

- GitHub: [sitharaj88](https://github.com/sitharaj88)
