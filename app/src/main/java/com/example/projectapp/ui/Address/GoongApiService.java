package com.example.projectapp.ui.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoongApiService {
    @GET("Place/AutoComplete")
    Call<PredictionResponse> getPredictions(@Query("input") String input, @Query("components") String components, @Query("api_key") String apiKey);
}
