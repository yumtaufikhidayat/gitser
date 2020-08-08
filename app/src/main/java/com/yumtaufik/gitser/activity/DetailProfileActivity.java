package com.yumtaufik.gitser.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.custom.DetailProfilePagerAdapter;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.model.search.SearchItems;
import com.yumtaufik.gitser.viewmodel.detail.DetailProfileViewModel;

public class DetailProfileActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL_PROFILE = "com.yumtaufik.gitser.activity.EXTRA_DETAIL_PROFILE";

    Toolbar toolbarDetail;
    
    ImageView imgUserProfile;
    TextView tvNameUserProfile,
            tvUsernameUserProfile,
            tvFollowingUserProfile,
            tvFollowersUserProfile,
            tvRepositoryUserProfile;

    TabLayout tabLayoutDetailProfile;
    ViewPager viewPagerDetailProfile;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    DetailProfileViewModel profileViewModel;
    SearchItems searchItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);

        setInit();

        setWindowNotificationBg();

        setGetSupportActionBar();

        DetailProfilePagerAdapter profilePagerAdapter = new DetailProfilePagerAdapter(getSupportFragmentManager(), tabLayoutDetailProfile.getTabCount());
        viewPagerDetailProfile.setAdapter(profilePagerAdapter);

        addOnTabSelected();
        viewPagerDetailProfile.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutDetailProfile));

        setViewModel();
    }

    private void setInit() {

        searchItems = getIntent().getParcelableExtra(EXTRA_DETAIL_PROFILE);

        toolbarDetail = findViewById(R.id.toolbarDetail);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        tvNameUserProfile = findViewById(R.id.tvNameUserProfile);
        tvUsernameUserProfile = findViewById(R.id.tvUsernameUserProfile);
        tvFollowingUserProfile = findViewById(R.id.tvFollowingUserProfile);
        tvFollowersUserProfile = findViewById(R.id.tvFollowersUserProfile);
        tvRepositoryUserProfile = findViewById(R.id.tvRepositoryUserProfile);

        tabLayoutDetailProfile = findViewById(R.id.tabLayoutDetailProfile);
        viewPagerDetailProfile = findViewById(R.id.viewPagerDetailProfile);

        errorLayout = findViewById(R.id.errorLayout);
        imgErrorImage = findViewById(R.id.imgErrorImage);
        tvErrorTitle = findViewById(R.id.tvErrorTitle);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
    }

    //----Method to set notification bar----
    private void setWindowNotificationBg() {

        Window windowNotifBg = this.getWindow();
        windowNotifBg.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            windowNotifBg.getDecorView().setSystemUiVisibility(0);
        }
    }
    //----Ends----

    //----Methods to set action bar----
    private void setGetSupportActionBar() {
        setSupportActionBar(toolbarDetail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + searchItems.getLogin() + "</font>")));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    //----Ends-----

    private void setViewModel() {

        String username = searchItems.getLogin();

        errorLayout.setVisibility(View.GONE);

        if (isNetworkAvailable()) {
            profileViewModel = new ViewModelProvider(this).get(DetailProfileViewModel.class);
            profileViewModel.getDetailProfileByUsername(username).observe(this, new Observer<DetailProfileResponse>() {
                @Override
                public void onChanged(DetailProfileResponse detailProfileResponse) {
                    if (detailProfileResponse != null) {

                        String photo = detailProfileResponse.getAvatarUrl();
                        String name = detailProfileResponse.getName();
                        String username = detailProfileResponse.getLogin();
                        Integer following = detailProfileResponse.getFollowing();
                        Integer followers = detailProfileResponse.getFollowers();
                        Integer repos = detailProfileResponse.getPublicRepos();

                        Glide.with(DetailProfileActivity.this)
                                .asBitmap()
                                .load(photo)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(imgUserProfile);

                        tvNameUserProfile.setText(name);
                        tvUsernameUserProfile.setText(username);
                        tvFollowingUserProfile.setText(String.valueOf(following));
                        tvFollowersUserProfile.setText(String.valueOf(followers));
                        tvRepositoryUserProfile.setText(String.valueOf(repos));
                    } else {
                        Log.i("onChangedFailed", "onChanged: ");
                    }
                }
            });
        }
    }

    //----Methods to check network connection---
    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

    //----Methods to set tab layout----
    private void addOnTabSelected() {
        tabLayoutDetailProfile.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerDetailProfile.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //----Ends----
}