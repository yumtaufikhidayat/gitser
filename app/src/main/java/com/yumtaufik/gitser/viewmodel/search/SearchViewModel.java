package com.yumtaufik.gitser.viewmodel.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.search.SearchItems;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository = new SearchRepository();

    public LiveData<List<SearchItems>> getSearchByUsername(String searchUser) {
        return searchRepository.searchUsersByUsername(searchUser);
    }
}
