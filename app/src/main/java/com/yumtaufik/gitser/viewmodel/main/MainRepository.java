package com.yumtaufik.gitser.viewmodel.main;

import androidx.lifecycle.MutableLiveData;

import com.yumtaufik.gitser.api.ApiClient;
import com.yumtaufik.gitser.api.ApiInterface;
import com.yumtaufik.gitser.model.main.MainResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainRepository {

    private final MutableLiveData<List<MainResponse>> mutableLiveDataAllusers = new MutableLiveData<>();

    MutableLiveData<List<MainResponse>> getAllUsers() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MainResponse>> getResponse = apiInterface.getAllUsers();
        getResponse.enqueue(new Callback<List<MainResponse>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<MainResponse>> call, Response<List<MainResponse>> response) {
                mutableLiveDataAllusers.setValue((ArrayList<MainResponse>) response.body());
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<MainResponse>> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });

        return mutableLiveDataAllusers;
    }
}
