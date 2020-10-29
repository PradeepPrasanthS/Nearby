package com.pradeep.nearby

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class NearbyPlacesActivity : AppCompatActivity() {

    private lateinit var lat: String
    private lateinit var lon: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_places)

        val extras = intent.extras
        extras?.let {
            lat = it.getString("lat", "")
            lon = it.getString("lon", "")
        }

        Log.i(TAG, "Latitude: $lat Longitude: $lon")
    }

    companion object{
        private const val TAG = "NearbyPlacesActivity"
    }
}