package com.app.mayur.retrofit;

import com.app.mayur.pojo.Species;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // getSpecies
    @GET("?")
    Call<Species> getSpecies(@Query("page") String pages);

}