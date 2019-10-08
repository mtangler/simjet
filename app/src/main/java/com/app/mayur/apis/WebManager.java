package com.app.mayur.apis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.app.mayur.pojo.Species;
import com.app.mayur.retrofit.ApiInterface;
import com.app.mayur.retrofit.LogEx;
import com.app.mayur.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebManager {

    Context context;

    public WebManager(Context context) {
        this.context = context;
    }

    public void getDataFromApi(int page, final ResultInterface resultInterface) {

        if (!isNetworkConnected()) {
            String message = "No Internet Connection";
            resultInterface.onMessage(message);
            toast(message);
            return;
        }

        ApiInterface apiService = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Species> call = apiService.getSpecies("" + page);

        call.enqueue(new Callback<Species>() {
            @Override
            public void onResponse(Call<Species> call, Response<Species> response) {

                List<Species.Result> data = new ArrayList<>();

                try {

                    LogEx.log(response.raw().toString());
                    data = response.body().getResults();
                    resultInterface.onResults(data, response.body().getCount());

                } catch (Exception e) {
                    LogEx.log(e);
                    resultInterface.onMessage(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Species> call, Throwable t) {
                LogEx.log(t.getMessage());
                resultInterface.onMessage(t.getMessage());
                toast(t.getMessage());
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
