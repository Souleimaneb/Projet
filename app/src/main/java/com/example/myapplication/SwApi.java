package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SwApi {
    @GET("api/films/")
    Call<RestFilmResponse> getFilmResponse();

}
