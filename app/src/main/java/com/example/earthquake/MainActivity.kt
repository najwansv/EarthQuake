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
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        } else {
            startWakeWordDetection()
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

    private fun performAction() {
        // Example actions:
        // 1. Show earthquake information
        // 2. Trigger an alert sound
        // 3. Navigate to a specific screen

        // For demo purposes:
        findViewById<TextView>(R.id.textView).text = "ACTIVATED!"
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startWakeWordDetection()
        } else {
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
