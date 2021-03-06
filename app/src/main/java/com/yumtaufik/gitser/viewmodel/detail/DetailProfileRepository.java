package com.yumtaufik.gitser.viewmodel.detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.api.ApiClient;
import com.yumtaufik.gitser.api.ApiInterface;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DetailProfileRepository extends ViewModel {

    private final MutableLiveData<DetailProfileResponse> mutableLiveDataDetailProfile = new MutableLiveData<>();

    MutableLiveData<DetailProfileResponse> detailProfileByUsername(String username) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DetailProfileResponse> getResponse = apiInterface.getDetailProfile(username);
        getResponse.enqueue(new Callback<DetailProfileResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<DetailProfileResponse> call, Response<DetailProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DetailProfileResponse profileResponse = response.body();
                    mutableLiveDataDetailProfile.setValue(profileResponse);
                    Log.i("onResponse", "onResponse: " + profileResponse);
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
            public void onFailure(Call<DetailProfileResponse> call, Throwable t) {
                Log.e("onFailureDetail", "onFailure: " + t.getLocalizedMessage());
            }
        });

        return mutableLiveDataDetailProfile;
    }
}
