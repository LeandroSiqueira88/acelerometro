package com.example.acelerometro

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var isSensorRunning = false

    private lateinit var txtX: TextView
    private lateinit var txtY: TextView
    private lateinit var txtZ: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtX = findViewById(R.id.txtX)
        txtY = findViewById(R.id.txtY)
        txtZ = findViewById(R.id.txtZ)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        btnStart.setOnClickListener {
            startSensor()
        }

        btnStop.setOnClickListener {
            stopSensor()
        }
    }

    private fun startSensor() {
        if (!isSensorRunning && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            isSensorRunning = true
        }
    }

    private fun stopSensor() {
        if (isSensorRunning) {
            sensorManager.unregisterListener(this)
            isSensorRunning = false
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        txtX.text = "Eixo X: ${event.values[0]}"
        txtY.text = "Eixo Y: ${event.values[1]}"
        txtZ.text = "Eixo Z: ${event.values[2]}"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        stopSensor()
    }

    override fun onResume() {
        super.onResume()
        // Você pode iniciar automaticamente ou não
        // startSensor()
    }
}
