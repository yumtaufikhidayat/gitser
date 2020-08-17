package com.yumtaufik.gitser.viewmodel.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.repository.RepositoryResponse;

import java.util.List;

public class ReposViewModel extends ViewModel {

    private final ReposRepository reposRepository = new ReposRepository();

    public LiveData<List<RepositoryResponse>> getAllRepos(String username) {
        return reposRepository.getAllRepos(username);
    }
}
