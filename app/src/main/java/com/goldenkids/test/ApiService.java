package com.goldenkids.test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("cctvInfo")
    Call<String> getCctvInfo(
            @Query("apiKey") String apiKey,
            @Query("type") String type,
            @Query("cctvType") String cctvType,
            @Query("minX") String minX,
            @Query("maxX") String maxX,
            @Query("minY") String minY,
            @Query("maxY") String maxY,
            @Query("getType") String getType
    );
}
