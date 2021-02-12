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

public class ActivityMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager() .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMap();
        LatLng field = new LatLng(Double.parseDouble(receivePositionX()),Double.parseDouble(receivePositionY()));
        mMap.addMarker(new MarkerOptions().position(field).title(receiveFieldName()));
        mMap.setMinZoomPreference(13);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(field));
    }

    private void updateMap(){
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