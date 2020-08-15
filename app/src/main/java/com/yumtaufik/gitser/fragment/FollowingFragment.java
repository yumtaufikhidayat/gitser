package com.yumtaufik.gitser.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yumtaufik.gitser.R;

public class FollowingFragment extends Fragment {

    public static final String EXTRA_FOLLOWING_FRAGMENT = "com.yumtaufik.gitser.fragment.EXTRA_FOLLOWING_FRAGMENT";

    String username;

    public FollowingFragment() {
        // Required empty public constructor
    }

    public void newInstance(String username) {
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FOLLOWING_FRAGMENT, username);
        followingFragment.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            username = getArguments().getString(EXTRA_FOLLOWING_FRAGMENT);
        }
    }
}