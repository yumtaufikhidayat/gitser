package com.yumtaufik.gitser.viewmodel.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.yumtaufik.gitser.api.ApiClient;
import com.yumtaufik.gitser.api.ApiInterface;
import com.yumtaufik.gitser.model.search.SearchItems;
import com.yumtaufik.gitser.model.search.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

class SearchRepository {

    private final MutableLiveData<List<SearchItems>> mutableLiveDataSearchItems = new MutableLiveData<>();

    MutableLiveData<List<SearchItems>> searchUsersByUsername(String searchUser) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SearchResponse> getResponse = apiInterface.searchUserByUsername(searchUser);
        getResponse.enqueue(new Callback<SearchResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse searchResponse = response.body();
                    if (searchResponse != null && searchResponse.getItems() != null) {
                        mutableLiveDataSearchItems.setValue(searchResponse.getItems());
                        Log.e("onSuccess", "onResponse: " + response.message());
                    }
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
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                mutableLiveDataSearchItems.setValue(new ArrayList<SearchItems>());
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveDataSearchItems;
    }
}
