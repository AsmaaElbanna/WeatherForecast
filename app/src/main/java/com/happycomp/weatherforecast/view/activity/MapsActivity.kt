package com.happycomp.weatherforecast.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ActivityMapsBinding
import com.happycomp.weatherforecast.databinding.FragmentHomeBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

   private lateinit var binding: ActivityMapsBinding

    private lateinit var mMap: GoogleMap
    var lat: Double = 0.0
    var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    binding.btnConfirm.setOnClickListener{
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("Lat",lat)
            putExtra("Long",long)

        })
        finish()
    }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { point ->
            lat = point.latitude
            long = point.longitude
            Toast.makeText(
                this,
                point.latitude.toString() + ", " + point.longitude,
                Toast.LENGTH_SHORT
            ).show()


        }


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}