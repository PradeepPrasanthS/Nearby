package com.pradeep.nearby

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class NearbyPlacesActivity : AppCompatActivity() {

    private lateinit var lat: String
    private lateinit var lon: String
    private lateinit var nearByPlacesList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_places)

        val nearBy = findViewById<ListView>(R.id.nearbyPlaces)
        nearByPlacesList = ArrayList()

        val extras = intent.extras
        extras?.let {
            lat = it.getString("lat", "")
            lon = it.getString("lon", "")
        }

        Log.i(TAG, "Latitude: $lat Longitude: $lon")

        val requestQueue = Volley.newRequestQueue(this)

        val url = "$BASE_URL?query=$QUERY&location=$lat,$lon&key=$KEY"
        Log.e(TAG, "URL: $url")

        val request =
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                try {
                    val results = response.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val nearby = results.getJSONObject(i)
                        val name = nearby.getString("name")
                        nearByPlacesList.add(name)
                        val adapter = ArrayAdapter(
                            this,
                            android.R.layout.simple_list_item_1,
                            nearByPlacesList
                        )
                        nearBy.adapter = adapter
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }, { error ->
                Log.e(TAG, "Exception: $error")
            })
        requestQueue?.add(request)
    }

    companion object {
        private const val TAG = "NearbyPlacesActivity"
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json"
        private const val KEY = "AIzaSyBOCZwNtgZ6HiIYzVyc6jJ3JicoNYCi9Uc"
        private const val QUERY = "restaurant"
    }
}