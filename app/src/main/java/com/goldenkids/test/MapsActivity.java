package com.goldenkids.test;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng apiLatLng = new LatLng(37.42889, 127.12361);

        // 파란색 마커 아이콘 생성
        float hue = BitmapDescriptorFactory.HUE_BLUE;
        BitmapDescriptor blueMarker = BitmapDescriptorFactory.defaultMarker(hue);

        // 마커 추가
        MarkerOptions markerOptions = new MarkerOptions()
                .position(apiLatLng)
                .title("마커 제목")
                .icon(blueMarker);

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(apiLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }
}
