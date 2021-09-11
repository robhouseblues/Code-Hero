package com.example.codehero.service;

import com.example.codehero.model.CallResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelService {
    @GET("characters?")
    Call<CallResponse> findAll(
            @Query("ts") long ts,
            @Query("offset") long offset,
            @Query("limit") long limit,
            @Query("apikey") String apikey,
            @Query("hash") String hash);

    @GET("characters?")
    Call<CallResponse> findByName(
            @Query("ts") long ts,
            @Query("nameStartsWith") String name,
            @Query("offset") long offset,
            @Query("limit") long limit,
            @Query("apikey") String apikey,
            @Query("hash") String hash);
}
