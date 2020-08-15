package com.yumtaufik.gitser.viewmodel.followers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;

import java.util.List;

public class FollowersViewModel extends ViewModel {

    private final FollowersRepository followersRepository = new FollowersRepository();

    public LiveData<List<FollowingFollowersResponse>> getProfileFollowers(String username) {
        return followersRepository.getProfileFollowers(username);
    }
}
