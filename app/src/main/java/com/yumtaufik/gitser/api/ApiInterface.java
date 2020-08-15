package com.yumtaufik.gitser.api;

import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.model.main.MainResponse;
import com.yumtaufik.gitser.model.search.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.USERS_URL)
    Call<List<MainResponse>> getAllUsers();

    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.SEARCH_USERS_URL)
    Call<SearchResponse> searchUserByUsername(@Query("q") String searchUser);

    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.DETAIL_PROFILE_URL)
    Call<DetailProfileResponse> getDetailProfile(@Path("username") String username);

    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.FOLLOWING_URL)
    Call<List<FollowingFollowersResponse>> getProfileFollowing(
            @Path(value = "username", encoded = true) String username
    );

    @Headers("Authorization: token " + Api.GITHUB_TOKEN)
    @GET(Api.FOLLOWERS_URL)
    Call<List<FollowingFollowersResponse>> getProfileFollowers(
            @Path(value = "username", encoded = true) String username
    );
}
