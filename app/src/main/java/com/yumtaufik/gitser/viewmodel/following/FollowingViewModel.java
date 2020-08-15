package com.yumtaufik.gitser.viewmodel.following;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;

import java.util.List;

public class FollowingViewModel extends ViewModel {

    private final FollowingRepository followingRepository = new FollowingRepository();

    public LiveData<List<FollowingFollowersResponse>> getProfileFollowing(String username) {
        return followingRepository.getProfileFollowing(username);
    }
}
