package com.example.practica3.androidimpl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.List;

public class AccelerometerHandler implements SensorEventListener {
    float accelX;
    float accelY;
    float accelZ;

    public AccelerometerHandler(Context context) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // Obtener la lista de sensores de tipo acelerómetro
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        // Usar isEmpty() en lugar de size() != 0
        if (!sensors.isEmpty()) {
            Sensor accelerometer = sensors.get(0);
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No hace nada
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accelX = event.values[0];
        accelY = event.values[1];
        accelZ = event.values[2];
    }

    public float getAccelX() {
        return accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public float getAccelZ() {
        return accelZ;
    }
}