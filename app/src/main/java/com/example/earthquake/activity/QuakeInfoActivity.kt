package com.example.earthquake.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.earthquake.R
import org.json.JSONObject
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class QuakeInfoActivity : AppCompatActivity() {
    private lateinit var webViewQuakeInfo: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: ImageView
    private lateinit var tvDateTime: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvMagnitude: TextView
    private lateinit var tvDepth: TextView
    private lateinit var tvRegion: TextView
    private lateinit var tvPotential: TextView
    private lateinit var tvFelt: TextView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quake_info)
        
        // Initialize views
        webViewQuakeInfo = findViewById(R.id.webViewQuakeInfo)
        progressBar = findViewById(R.id.progressBar)
        backButton = findViewById(R.id.back)
        tvDateTime = findViewById(R.id.tvDateTime)
        tvLocation = findViewById(R.id.tvLocation)
        tvMagnitude = findViewById(R.id.tvMagnitude)
        tvDepth = findViewById(R.id.tvDepth)
        tvRegion = findViewById(R.id.tvRegion)
        tvPotential = findViewById(R.id.tvPotential)
        tvFelt = findViewById(R.id.tvFelt)

        // Setup back button
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Configure WebView
        webViewQuakeInfo.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            loadUrl("https://warning.bmkg.go.id/")
        }

        // Fetch earthquake data
        fetchEarthquakeData()
    }

    private fun fetchEarthquakeData() {
        progressBar.visibility = View.VISIBLE
        
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json")
                val connection = url.openConnection()
                val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
                val response = StringBuilder()
                var line: String?
                
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                
                val jsonResponse = response.toString()
                withContext(Dispatchers.Main) {
                    parseEarthquakeData(jsonResponse)
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    // Handle error
                }
            }
        }
    }

    private fun parseEarthquakeData(jsonData: String) {
        try {
            val jsonObject = JSONObject(jsonData)
            val infogempa = jsonObject.getJSONObject("Infogempa")
            val gempa = infogempa.getJSONObject("gempa")
            
            val tanggal = gempa.getString("Tanggal")
            val jam = gempa.getString("Jam")
            val lintang = gempa.getString("Lintang")
            val bujur = gempa.getString("Bujur")
            val magnitude = gempa.getString("Magnitude")
            val kedalaman = gempa.getString("Kedalaman")
            val wilayah = gempa.getString("Wilayah")
            val potensi = gempa.getString("Potensi")
            val dirasakan = gempa.getString("Dirasakan")
            
            tvDateTime.text = "Date/Time: $tanggal $jam"
            tvLocation.text = "Location: $lintang - $bujur"
            tvMagnitude.text = "Magnitude: $magnitude"
            tvDepth.text = "Depth: $kedalaman"
            tvRegion.text = "Region: $wilayah"
            tvPotential.text = "Potential: $potensi"
            tvFelt.text = "Felt: $dirasakan"
        } catch (e: Exception) {
            // Handle parsing error
        }
    }
}
