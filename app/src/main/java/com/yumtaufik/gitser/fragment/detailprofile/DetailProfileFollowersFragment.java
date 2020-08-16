package com.yumtaufik.gitser.fragment.detailprofile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.followingfollowers.FollowingFollowersAdapter;
import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;
import com.yumtaufik.gitser.viewmodel.followers.FollowersViewModel;

import java.util.List;

public class DetailProfileFollowersFragment extends Fragment {

    public static final String ARGS_FOLLOWERS_FRAGMENT = "com.yumtaufik.gitser.fragment.EXTRA_FOLLOWERS_FRAGMENT";

    FollowingFollowersAdapter adapter;
    RecyclerView rvFollowers;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    String username;

    public DetailProfileFollowersFragment() {
        // Required empty public constructor
    }

    public static DetailProfileFollowersFragment newInstance(String username) {
        DetailProfileFollowersFragment fragment = new DetailProfileFollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_FOLLOWERS_FRAGMENT, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_profile_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFollowers = view.findViewById(R.id.rvDetailProfileFollowers);

        errorLayout = view.findViewById(R.id.errorLayout);
        imgErrorImage = view.findViewById(R.id.imgErrorImage);
        tvErrorTitle = view.findViewById(R.id.tvErrorTitle);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);
        
        setFollowersArguments();

        setViewModel();
    }

    private void setFollowersArguments() {
        if (getArguments() != null) {
            username = getArguments().getString(ARGS_FOLLOWERS_FRAGMENT);
        } else {
            Log.i("argumentsNull", "onViewCreated: ");
        } 
    }

    private void setViewModel() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        rvFollowers.setLayoutManager(layoutManager);
        rvFollowers.setHasFixedSize(true);
        rvFollowers.setItemAnimator(new DefaultItemAnimator());
        rvFollowers.setNestedScrollingEnabled(false);

        adapter = new FollowingFollowersAdapter();
        rvFollowers.setAdapter(adapter);

        FollowersViewModel viewModel = new ViewModelProvider(this).get(FollowersViewModel.class);
        if (isNetworkAvailable()) {
            viewModel.getProfileFollowers(username).observe(getViewLifecycleOwner(), new Observer<List<FollowingFollowersResponse>>() {
                @Override
                public void onChanged(List<FollowingFollowersResponse> followingFollowersResponses) {
                    if (followingFollowersResponses.size() > 0) {
                        adapter.setDataFollowingFollowers(followingFollowersResponses);
                    } else {
                        showErrorMessage(R.drawable.no_result, R.string.tvNoResult, R.string.tvNoResultDesc);
                        errorLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            showErrorMessage(R.drawable.ic_no_connection, R.string.tvOops, R.string.tvCheckYourConnection);
        }
    }

    //----Methods to check network connection---
    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager manager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;

            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }
    }
    //---Ends----

    //----Method to show error connection----
    private void showErrorMessage(Integer image, Integer title, Integer message) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        imgErrorImage.setImageResource(image);
        tvErrorTitle.setText(title);
        tvErrorMessage.setText(message);
    }
    //----Ends----
}