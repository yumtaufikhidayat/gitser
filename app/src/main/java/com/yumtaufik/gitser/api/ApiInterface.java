package com.yumtaufik.gitser.api;

import com.yumtaufik.gitser.model.search.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers("Authorization: token 76bcefd11dadb523b91dd6cb4545025f0a27fccd")
    @GET("search/users?")
    Call<SearchResponse> searchUserByUsername(@Query("q") String searchUser);
}
