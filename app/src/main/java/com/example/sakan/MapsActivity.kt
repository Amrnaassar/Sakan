package com.example.sakan

import android.content.ContentProviderClient
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.sakan.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.rooms.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    override fun onMapReady(googleMap: GoogleMap) {
        var point=LatLng(34.1,151.0)
        mMap = googleMap
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID)
        mMap.getUiSettings().setZoomControlsEnabled(true)

        ////// permission and currrent Location /////////
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
            val task=fusedLocationProviderClient.lastLocation

            if( ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
                Log.d("Find","btnSaveLocationPermission")

                return
            }

            task.addOnSuccessListener {
                Log.d("Find","MyLocation1")

                if(it!=null)
                {
                    Log.d("Find","MyLocation2")

                    var pt = LatLng(it.latitude, it.longitude)
                    mMap.addMarker(MarkerOptions().position(pt).title("Me"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pt,16f))
                    Toast.makeText(this,pt.toString(),Toast.LENGTH_LONG).show()
                    point=pt
                }
            }



        // Add a marker in Sydney and move the camera


        mMap.setOnMapClickListener { p0: LatLng? ->
            if( p0 != null ) {
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(p0).title("Location"))
                Toast.makeText(this,p0.toString(),Toast.LENGTH_LONG).show()
                point =p0
            }

            btnSaveLocation.setOnClickListener{
                Log.d("Find","btnSaveLocation"+point.toString())
                val intent= Intent(this,AddPostActivity::class.java)

                intent.putExtra("latitude",point.latitude.toString() )
                intent.putExtra("longitude",point.longitude.toString())
                intent.putExtra("Visible","on" )
                startActivity(intent)
                Log.d("Find","btnSaveLocationEnd")

            }
        }




    }



}