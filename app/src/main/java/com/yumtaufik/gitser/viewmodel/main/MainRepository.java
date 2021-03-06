package com.yumtaufik.gitser.viewmodel.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.yumtaufik.gitser.api.ApiClient;
import com.yumtaufik.gitser.api.ApiInterface;
import com.yumtaufik.gitser.model.main.MainResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainRepository {

    private final MutableLiveData<List<MainResponse>> mutableLiveDataAllUsers = new MutableLiveData<>();

    MutableLiveData<List<MainResponse>> getAllUsers() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<MainResponse>> getResponse = apiInterface.getAllUsers();
        getResponse.enqueue(new Callback<List<MainResponse>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<MainResponse>> call, Response<List<MainResponse>> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataAllUsers.setValue(response.body());
                } else {
                    switch (response.code()) {
                        case 404:
                            Log.e("404", "error404: " + response.errorBody());
                            break;

                        case 500:
                            Log.e("500", "error500: " + response.errorBody());
                            break;

                        default:
                            break;
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<MainResponse>> call, Throwable t) {
                Log.e("allUsersError", "onFailure: " + t.getLocalizedMessage());
            }
        });

        return mutableLiveDataAllUsers;
    }
}
