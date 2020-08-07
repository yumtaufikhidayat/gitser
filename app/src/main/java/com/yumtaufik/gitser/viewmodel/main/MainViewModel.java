package com.yumtaufik.gitser.viewmodel.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yumtaufik.gitser.model.main.MainResponse;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final MainRepository mainRepository = new MainRepository();

    public LiveData<List<MainResponse>> getAllUsers() {
        return mainRepository.getAllUsers();
    }
}
