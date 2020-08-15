package com.yumtaufik.gitser.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yumtaufik.gitser.R;

public class FollowersFragment extends Fragment {

    public static final String EXTRA_FOLLOWERS_FRAGMENT = "com.yumtaufik.gitser.fragment.EXTRA_FOLLOWERS_FRAGMENT";

    public FollowersFragment() {
        // Required empty public constructor
    }

    public void newInstance(String username) {
        FollowersFragment followersFragment = new FollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FOLLOWERS_FRAGMENT, username);
        followersFragment.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}