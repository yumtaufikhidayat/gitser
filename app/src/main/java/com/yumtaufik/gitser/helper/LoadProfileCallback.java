package com.yumtaufik.gitser.helper;

import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.util.ArrayList;

public interface LoadProfileCallback {

    void preExecute();

    void postExecute(ArrayList<DetailProfileResponse> profileResponses);
}
