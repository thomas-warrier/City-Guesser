package com.example.projetfinalekotlin

import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.retrofit.Address
import com.example.projetfinalekotlin.retrofit.LongitudeLatitude
import com.example.projetfinalekotlin.retrofit.Result

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt


internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var counterMaker = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        counterMaker = 0
        mMap = googleMap

        mMap.setMapStyle(MapStyleOptions(resources.getString(R.string.map_style)))


        val addressCountryNullable = getAddressCountry()
        val addressCapitalNullable = getAddressCapital()
        val capitalName = intent.getStringExtra("capitalName")

        val mapTextView = findViewById<TextView>(R.id.map_text)
        mapTextView.text = "Posez un marker pour chercher la capital : $capitalName"


        addressCountryNullable?.let { addressCountry ->

            val b = addressCountry.geometry.bounds
            val bounds = LatLngBounds(
                LatLng(b.southwest.lat, b.southwest.lng),
                LatLng(b.northeast.lat, b.northeast.lng),
            )

            addressCapitalNullable?.let { addressCapital ->
                val locationCapital = addressCapital.geometry.location
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, .0), 0f))
                Timer().schedule(1000) {
                    runOnUiThread {

                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))

                        mMap.setLatLngBoundsForCameraTarget(bounds)
                    }
                }

                mMap.setOnMapClickListener { posMarker ->
                    val distanceEntre = addMarker(posMarker, locationCapital)

                    val distanceArrondie = (distanceEntre / 1000).roundToInt()
                    if (distanceArrondie < 50) {//WIN
                        startWinActivity(isWin(counterMaker))
                    } else if (!isWin(counterMaker)) {//LOOSE
                        startWinActivity(false)
                    }
                    mapTextView.text = "Vous êtes à ${distanceArrondie}km de $capitalName"

                }

            }


        }
    }


    private fun getAddressCapital(): Result? {
        var address: Address? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            address = intent.getSerializableExtra("capitalAddress", Address::class.java)
        } else {
            intent.getStringExtra("capitalAddress")?.let {
                address = Klaxon().parse<Address>(it)
            }
        }
        return getFirstResult(address)
    }

    private fun getAddressCountry(): Result? {
        var address: Address? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            address = intent.getSerializableExtra("address", Address::class.java)
        } else {
            intent.getStringExtra("address")?.let {
                address = Klaxon().parse<Address>(it)
            }
        }
        return getFirstResult(address)
    }

    private fun getFirstResult(address: Address?): Result? {
        address?.let { addressNotNull ->
            if (addressNotNull.results.isNotEmpty()) {
                return addressNotNull.results[0]
            }
        }
        return null
    }


    private fun isWin(counterMarker: Int): Boolean {
        return counterMarker < 5
    }

    private fun startWinActivity(isWin: Boolean) {
        val victoryIntent = Intent(this@MapsActivity, VictoryActivity::class.java)
        victoryIntent.putExtra(VictoryActivity.WIN_EXTRA, isWin)
        startActivity(victoryIntent)
        finish()
    }

    // define function to add marker at given lat & lng
    private fun addMarker(latLng: LatLng, findCityLocation: LongitudeLatitude): Float {
        counterMaker++
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latLng))
        val startPoint = Location("locationA")
        startPoint.latitude = latLng.latitude
        startPoint.longitude = latLng.longitude
        val endPoint = Location("locationB")
        endPoint.latitude = findCityLocation.lat
        endPoint.longitude = findCityLocation.lng
        return startPoint.distanceTo(endPoint)
    }
}
