package com.example.corseworkapp;

/* References
*
* Google, 2021. Get the last known location. [Online]
Available at: https://developer.android.com/training/location/retrieve-current
[Accessed 5 April 2021].
Google, 2021. Quickstart - Adding a Map. [Online]
Available at: https://developers.google.com/maps/documentation/android-sdk/start
[Accessed 5 April 2021].
Google, 2021. Using API Keys. [Online]
Available at: https://developers.google.com/maps/documentation/android-sdk/get-api-key
[Accessed 5 April 2021].

*
* Google documentation was used in order to help create the map activity and set up the API key
* */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private final static int LocPerCode = 1;
    private static final String Fine_location = Manifest.permission.ACCESS_FINE_LOCATION;
    private Boolean LocationPermissionsConfirmed = false;
    private static final float ZoomIn = 15f;
    private FusedLocationProviderClient mFusedLocationClient;
    private EditText LocationSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        BottomNavigationView NavBot = findViewById(R.id.NavigationBar);

        Menu Menu = NavBot.getMenu();
        MenuItem MenuItems = Menu.getItem(1);
        MenuItems.setChecked(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        NavBot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home_Icon:
                        overridePendingTransition(0, 0);
                        //Intent HomeIntent = new Intent(MapsActivity.this, HomeScreen.class);
                        finish();
                        //startActivity(HomeIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Map_Icon:
                        break;

                    case R.id.Calander_Icon:
                        overridePendingTransition(0, 0);
                        Intent CalanderIntent = new Intent(MapsActivity.this, Callander.class);
                        finish();
                        startActivity(CalanderIntent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Timer_Icon:
                        overridePendingTransition(0, 0);
                        Intent TimerIntent = new Intent(MapsActivity.this, timer.class);
                        finish();
                        startActivity(TimerIntent);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });

        LocationPermission();

        if(LocationPermissionsConfirmed == true)
        {
            LastKnownLocation();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void LastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (LocationPermissionsConfirmed == true) {
                Task location = mFusedLocationClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location CurLoc = (Location) task.getResult();
                            CameraMove(new LatLng(CurLoc.getLatitude(), CurLoc.getLongitude()), ZoomIn);
                        } else {
                            Toast.makeText(MapsActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    private void CameraMove(LatLng latLng, float ZoomIn) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZoomIn));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (LocationPermissionsConfirmed == true) {
            LastKnownLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
        LatLng Driftwood = new LatLng(55.86647220631045,  -4.270541906692374);
        mMap.addMarker(new MarkerOptions().position(Driftwood).title("Driftwood"));
        LatLng FireWater = new LatLng(55.865614740088866, -4.265913985749616);
        mMap.addMarker(new MarkerOptions().position(FireWater).title("FireWater"));
        LatLng Garage = new LatLng(55.86631414727771, -4.268344553703313);
        mMap.addMarker(new MarkerOptions().position(Garage).title("Garage"));
        LatLng  Bamboo = new LatLng(55.8631647175179, -4.255926816833805);
        mMap.addMarker(new MarkerOptions().position(Bamboo).title("Bamboo"));
        LatLng Cathouse = new LatLng(55.858679787641265, -4.256796124868345);
        mMap.addMarker(new MarkerOptions().position(Cathouse).title("Cathouse"));
    }

    private void LocationPermission()
    {
        String[] permission =
        {
            Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Fine_location) == PackageManager.PERMISSION_GRANTED)
        {
            LocationPermissionsConfirmed= true;
        }
        else
        {
            ActivityCompat.requestPermissions(this, permission, LocPerCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case LocPerCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    LocationPermissionsConfirmed = true;
                    return;
                }
        }
    }
}