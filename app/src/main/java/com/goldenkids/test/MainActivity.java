package com.goldenkids.test;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.Manifest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ApiService apiService;

    // CCTV api 호출
    private class FetchCctvDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

      //  executeCctvDataTask(); //AsyncTask
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.its.go.kr:9443/")
                .addConverterFactory(ScalarsConverterFactory.create())  // ScalarsConverterFactory를 사용하여 문자열로 변환
                .build();

        // ApiService 인스턴스 생성
        ApiService apiService = retrofit.create(ApiService.class);

        // API 호출
        Call<String> call = apiService.getCctvInfo("1b3009aee895424bbaea43c7d7f6a13d", "all", "1", "126.8845475", "127.8845475", "35.8504119", "36.8504119", "xml");

        // 비동기적으로 실행
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseBody = response.body();
                    //System.out.println(responseBody);
                    ArrayList<Jung> jungList = Jung.parseJungData(responseBody);
                    for (Jung jung : jungList) {
                        //LatLng currentLatLng = new LatLng(jung.getCoordY(),jung.getCoordX());
                        setmaker(jung.getCoordY(),jung.getCoordX(),jung.getCctvName());

                    }
                    // 여기에서 문자열을 원하는 방식으로 처리할 수 있습니다.
                    // 예: 필요한 경우 문자열을 XML 파서를 사용하여 파싱
                } else {
                    // 에러 처리
                    System.out.println("에러: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // 실패 처리
                t.printStackTrace();
            }
        });
    }


    //AsyncTask 메소드
    private void executeCctvDataTask() {
        new FetchCctvDataTask().execute();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // 여기서 CCTV의 상태를 확인하고, 상태에 따라 마커를 추가하고 색상을 설정
                    boolean isCctvFlooded = isCctvFlooded();

                    if (isCctvFlooded) {
                        // CCTV가 물에 잠겼을 경우 빨간색 마커로 추가--> 이미지 추가

                        addRedMarker(currentLatLng, "CCTV (Flooded)");
                    } else {
                        // 이미지가 없는 경우 기본 마커로 추가
                        setmaker(currentLatLng.latitude, currentLatLng.longitude, "me");
                    }
                    
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                }
            }
        });
    }

    private void addRedMarker(LatLng currentLatLng, String s) {
        mMap.addMarker(new MarkerOptions());
    }

    // 특정 조건에 따라 CCTV의 상태를 확인하는 메서드
    private boolean isCctvFlooded() {
        // 여기에 이미지 추가시 마커가 바뀌는 코드 작성

        return true;
    }

    public void setmaker(double lat,double lang,String location){
        LatLng currentLatLng = new LatLng(lat,lang);
        mMap.addMarker(new MarkerOptions().position(currentLatLng).title(location));

    }

}