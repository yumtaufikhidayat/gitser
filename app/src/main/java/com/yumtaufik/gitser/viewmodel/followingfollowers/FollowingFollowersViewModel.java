package com.yumtaufik.gitser.viewmodel.followingfollowers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;

import java.util.List;

public class FollowingFollowersViewModel extends ViewModel {

    private final FollowingFollowersRepository followingFollowersRepository = new FollowingFollowersRepository();

    public LiveData<List<FollowingFollowersResponse>> getProfileFollowing(String username) {
        return followingFollowersRepository.getProfileFollowing(username);
    }
}
