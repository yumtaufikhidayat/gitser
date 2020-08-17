package com.yumtaufik.gitser.viewmodel.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.yumtaufik.gitser.api.ApiClient;
import com.yumtaufik.gitser.api.ApiInterface;
import com.yumtaufik.gitser.model.repository.RepositoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ReposRepository {

    private final MutableLiveData<List<RepositoryResponse>> mutableLiveDataRepos = new MutableLiveData<>();

    MutableLiveData<List<RepositoryResponse>> getAllRepos(String username) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<RepositoryResponse>> getResponse = apiInterface.getProfileRepository(username);
        getResponse.enqueue(new Callback<List<RepositoryResponse>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<RepositoryResponse>> call, Response<List<RepositoryResponse>> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataRepos.setValue(response.body());
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
            public void onFailure(Call<List<RepositoryResponse>> call, Throwable t) {
                Log.e("reposError", "onFailure: " + t.getLocalizedMessage());
            }
        });

        return mutableLiveDataRepos;
    }
}
