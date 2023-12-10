package com.goldenkids.test;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;

import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ApiService apiService;
    //    private Object BitmapDescriptorFactory;
    private String cctvUrl;

//    Dialog image_dialog;

//    public MainActivity(ApiService apiService, Object bitmapDescriptorFactory, String photoUrl) {
//        this.apiService = apiService;
//        this.BitmapDescriptorFactory = bitmapDescriptorFactory;
//        this.photoUrl = photoUrl;
//    }


    //AsyncTask 메소드
//    private void executeCctvDataTask() {
//        new FetchCctvDataTask().execute();
//    }

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
        MapsInitializer.initialize(this);
        //bitmapDescriptorFactory = com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.its.go.kr:9443/")
                .addConverterFactory(ScalarsConverterFactory.create())  // ScalarsConverterFactory를 사용하여 문자열로 변환
                .build();

        // ApiService 인스턴스 생성
        ApiService apiService = retrofit.create(ApiService.class);


        // API 호출
        Call<String> call = apiService.getCctvInfo("1b3009aee895424bbaea43c7d7f6a13d", "all", "2", "126.8845475", "127.8845475", "35.8504119", "36.8504119", "xml");

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

                        setmaker(jung.getCoordY(), jung.getCoordX(), jung.getCctvName(), jung.getCctvUrl());


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




    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        //위치 권한 요청
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//            return;
//        }


        // 마커 클릭 리스너 설정
        mMap.setOnMarkerClickListener(this);

        // 마커 추가
//        MarkerOptions markerOptions = new MarkerOptions();

        // API 호출 결과로 받은 좌표에 파란색 마커 추가
        LatLng apiLatLng = new LatLng(36.715244, 127.439159);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(apiLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5.0f));

    }

    public void setmaker(double lat,double lang,String location,String cctvUrl){
        LatLng currentLatLng = new LatLng(lat,lang);
        float hue = BitmapDescriptorFactory.HUE_BLUE;

        // MarkerOptions를 사용하여 파란색 마커 추가
        MarkerOptions markerOptions = new MarkerOptions()
                .position(currentLatLng)
                .snippet(cctvUrl)
                .title(location);
        if(location.equals("[중부선] 오창")){

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(hue));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        }

        mMap.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        //이미지를 보여주는 다이얼로그 메서드 호출
//        showImageDialog(brandTag);
        //임시,

        if(marker.getTitle().equals("[중부선] 오창")){
            showImageDialog();
        }else{

            showVideoViewDialog(marker.getSnippet());
        }
        return true;
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_dialog, null); // image_dialog_layout.xml 파일을 만들어야 합니다.

        // 예시: 브랜드 태그에 따라 이미지를 설정
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.a_1);
//        switch (brandTag) {
//            case 21:
//                imageView.setImageResource(R.drawable.a_1);
//                break;
//            case 23:
//                // 다른 브랜드에 대한 이미지 설정
//                break;
//            // 추가적인 브랜드에 대한 처리를 계속해서 추가할 수 있습니다.
//            default:
//                // 기본 이미지 설정
//                break;
//        }


        builder.setView(dialogView)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인 버튼을 눌렀을 때의 동작
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showVideoViewDialog(String cctvurl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.media_dialog, null);
        VideoView video = dialogView.findViewById(R.id.videoView);
        video.setVideoURI(Uri.parse(cctvurl));

        builder.setView(dialogView)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 확인 버튼을 눌렀을 때의 동작
                        dialog.dismiss(); // 다이얼로그 닫기

                    }
                });
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 확인 버튼을 눌렀을 때의 동작
//                    }
//                });
        video.start();

        AlertDialog dialog = builder.create();
        dialog.show();


    }









}