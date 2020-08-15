package com.yumtaufik.gitser.viewmodel.following;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.yumtaufik.gitser.api.ApiClient;
import com.yumtaufik.gitser.api.ApiInterface;
import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class FollowingRepository {

    private final MutableLiveData<List<FollowingFollowersResponse>> mutableLiveDataFollowingFollowers = new MutableLiveData<>();

    MutableLiveData<List<FollowingFollowersResponse>> getProfileFollowingFollowers(String username) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<FollowingFollowersResponse>> getResponse = apiInterface.getProfileFollowing(username);
        getResponse.enqueue(new Callback<List<FollowingFollowersResponse>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<FollowingFollowersResponse>> call, Response<List<FollowingFollowersResponse>> response) {
                if (response.isSuccessful()) {
                    mutableLiveDataFollowingFollowers.setValue(response.body());
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
            public void onFailure(Call<List<FollowingFollowersResponse>> call, Throwable t) {
                Log.e("followingError", "onFailure: " + t.getLocalizedMessage());
            }
        });

        return mutableLiveDataFollowingFollowers;
    }
}
