package com.yumtaufik.gitser.viewmodel.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

public class DetailProfileViewModel extends ViewModel {

    private final DetailProfileRepository profileRepository = new DetailProfileRepository();

    public LiveData<DetailProfileResponse> getDetailProfileByUsername(String username) {
        return profileRepository.detailProfileByUsername(username);
    }
}
