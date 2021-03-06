package com.yumtaufik.gitser.fragment.detailmain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.repository.RepositoryAdapter;
import com.yumtaufik.gitser.model.repository.RepositoryResponse;
import com.yumtaufik.gitser.viewmodel.repository.ReposViewModel;

import java.util.List;

public class DetailMainReposFragment extends Fragment {

    private static final String ARG_REPOSITORY_FRAGMENT = "com.yumtaufik.gitser.fragment.detailmain.ARG_REPOSITORY_FRAGMENT";

    RepositoryAdapter adapter;
    RecyclerView rvDetailMainRepos;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;
    
    String username;

    public DetailMainReposFragment() {
        // Required empty public constructor
    }
    
    public static DetailMainReposFragment newInstance(String username) {
        DetailMainReposFragment fragment = new DetailMainReposFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_REPOSITORY_FRAGMENT, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_main_repos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDetailMainRepos = view.findViewById(R.id.rvDetailMainRepos);

        errorLayout = view.findViewById(R.id.errorLayout);
        imgErrorImage = view.findViewById(R.id.imgErrorImage);
        tvErrorTitle = view.findViewById(R.id.tvErrorTitle);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);

        setRepositoryArguments();

        setViewModel();
    }

    private void setRepositoryArguments() {
        if (getArguments() != null) {
            username = getArguments().getString(ARG_REPOSITORY_FRAGMENT);
        } else {
            Log.i("reposArgsNull", "reposArgsNull: ");
        }
    }

    private void setViewModel() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        rvDetailMainRepos.setLayoutManager(layoutManager);
        rvDetailMainRepos.setHasFixedSize(true);
        rvDetailMainRepos.setItemAnimator(new DefaultItemAnimator());
        rvDetailMainRepos.setNestedScrollingEnabled(false);

        adapter = new RepositoryAdapter();
        rvDetailMainRepos.setAdapter(adapter);

        ReposViewModel viewModel = new ViewModelProvider(this).get(ReposViewModel.class);
        if (isNetworkAvailable()) {
            viewModel.getAllRepos(username).observe(getViewLifecycleOwner(), new Observer<List<RepositoryResponse>>() {
                @Override
                public void onChanged(List<RepositoryResponse> repositoryResponses) {
                    if (repositoryResponses.size() > 0) {
                        adapter.setDataRepository(repositoryResponses);
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