package com.example.earthquake

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ai.picovoice.porcupine.PorcupineManager
import ai.picovoice.porcupine.PorcupineManagerCallback
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var porcupineManager: PorcupineManager
    private  val TAG = "porcupineDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ask for microphone permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),1
            )
        } else {
            startWakeWordDetection()
        }

        // Ask for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                2
            )
        }

        // Set up button click listeners
        findViewById<Button>(R.id.buttonQuakeInfo).setOnClickListener {
            startActivity(Intent(this, QuakeInfoActivity::class.java))
        }

        findViewById<Button>(R.id.buttonSafeRoute).setOnClickListener {
            startActivity(Intent(this, SafeRouteActivity::class.java))
        }

        findViewById<Button>(R.id.buttonEmergency).setOnClickListener {
            startActivity(Intent(this, EmergencyActivity::class.java))
        }

        findViewById<Button>(R.id.buttonSafetyTips).setOnClickListener {
            startActivity(Intent(this, SafetyTipsActivity::class.java))
        }

        findViewById<Button>(R.id.buttonConsult).setOnClickListener {
            startActivity(Intent(this, ConsultActivity::class.java))
        }

        findViewById<ImageView>(R.id.gearIcon).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun startWakeWordDetection() {
        val callback = PorcupineManagerCallback { keywordIndex ->
            runOnUiThread {
                // Visual feedback
                Toast.makeText(this@MainActivity, "Wake word detected!", Toast.LENGTH_SHORT).show()

                // Example: Launch a specific activity or function
                performAction()
            }
        }

        try {
            porcupineManager = PorcupineManager.Builder()
                .setAccessKey("IQo9O8V2DszMWo7OZe+zuC5d2/s/y5ulw+/FcMfY+V6HWjccLgiFGA==") // replace this
                .setKeywordPath("Quake-safe_en_android_v3_0_0.ppn")      // must be in assets/
                .setSensitivity(0.9f)
                .build(applicationContext, callback)

            android.util.Log.d(TAG, "PorcupineManager created successfully")
            porcupineManager.start()
            android.util.Log.d(TAG, "PorcupineManager started successfully")

        } catch (e: Exception) {
            e.printStackTrace()
            android.util.Log.e(TAG, "Porcupine initialization error: ${e.javaClass.simpleName}: ${e.message}")
            android.util.Log.e(TAG, "Stack trace: ", e)
            Toast.makeText(this, "Error starting Porcupine: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCurrentLocationAndSend() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val timestamp = System.currentTimeMillis()

                sendViaWhatsApp(latitude, longitude, timestamp)
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendViaWhatsApp(lat: Double, lon: Double, timeMillis: Long) {
        val message = "Emergency Alert!\nLocation: https://maps.google.com/?q=$lat,$lon\nTime: ${java.util.Date(timeMillis)}"
        val phoneNumber = "6285718010794" // Nomor WA tujuan tanpa tanda '+'

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
        startActivity(intent)
    }


    private fun performAction() {
        // Example actions:
        // 1. Show earthquake information
        // 2. Trigger an alert sound
        // 3. Navigate to a specific screen

        // For demo purposes:
//        findViewById<TextView>(R.id.textView).text = "ACTIVATED!"
//
//        val intent = Intent(this, EmergencyActivity::class.java)
//        startActivity(intent)
        getCurrentLocationAndSend()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startWakeWordDetection()
        } else if (requestCode == 2 && grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Microphone permission is required", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            porcupineManager.stop()
            porcupineManager.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
