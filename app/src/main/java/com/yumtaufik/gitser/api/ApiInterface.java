package com.yumtaufik.gitser.api;

import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.model.search.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.SEARCH_USERS)
    Call<SearchResponse> searchUserByUsername(@Query("q") String searchUser);

    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.DETAIL_PROFILE)
    Call<DetailProfileResponse> detailProfile(@Path("username") String username);
}
