package com.example.projetfinalekotlin

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.retrofit.Address

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import java.util.*
import kotlin.concurrent.schedule


internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var address: Address? = null
        var addressCapital: Address? = null
        val capitalName = intent.getStringExtra("capitalName")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            address = intent.getSerializableExtra("address", Address::class.java)
            addressCapital = intent.getSerializableExtra("capitalAddress", Address::class.java)
        } else {
            intent.getStringExtra("address")?.let {
                address = Klaxon().parse<Address>(it)
            }

            intent.getStringExtra("addressCapital")?.let {
                addressCapital = Klaxon().parse<Address>(it)
            }
        }
        address?.let {
            if (it.results.isNotEmpty()) {
                val b = it.results[0].geometry.bounds
                val bounds = LatLngBounds(
                    LatLng(b.southwest.lat, b.southwest.lng),
                    LatLng(b.northeast.lat, b.northeast.lng),
                )

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, .0), 0f))
                Timer().schedule(1000) {
                    runOnUiThread {


                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))

                        mMap.setLatLngBoundsForCameraTarget(bounds)
                    }
                }

            }
        }


    }
}
