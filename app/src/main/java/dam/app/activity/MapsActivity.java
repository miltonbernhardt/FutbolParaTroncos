package dam.app.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dam.app.R;
import dam.app.recycler.FieldRecycler;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();
        //receivePositionX();
        //receivePositionY();
        // Add a marker in Sydney and move the camera
        LatLng field = new LatLng(Double.parseDouble(receivePositionX()),Double.parseDouble(receivePositionY()));
        mMap.addMarker(new MarkerOptions().position(field).title(receiveFieldName()));
        //mMap.setMaxZoomPreference(3);
        mMap.setMinZoomPreference(13);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(field));

    }

    private void actualizarMapa(){
        if (ActivityCompat.checkSelfPermission (this , Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    private String receivePositionX(){
        Bundle extras = getIntent().getExtras();
        String x = extras.getString("positionX");
        Log.d("positionX", x);
        return x;
    }
    private String receivePositionY(){
        Bundle extras = getIntent().getExtras();
        String y = extras.getString("positionY");
        Log.d("positionY", y);
        return y;
    }
    private String receiveFieldName(){
        Bundle extras = getIntent().getExtras();
        String field = extras.getString("fieldName");
        Log.d("fieldName", field);
        return field;
    }
}