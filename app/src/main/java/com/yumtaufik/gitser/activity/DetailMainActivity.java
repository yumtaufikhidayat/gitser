package com.yumtaufik.gitser.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
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
import com.yumtaufik.gitser.adapter.custom.detailmain.DetailMainPagerAdapter;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.model.main.MainResponse;
import com.yumtaufik.gitser.viewmodel.detail.DetailProfileViewModel;

public class DetailMainActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL_PROFILE_MAIN = "com.yumtaufik.gitser.activity.EXTRA_DETAIL_PROFILE_MAIN";

    Toolbar toolbarDetailMain;

    ImageView imgMain;
    TextView tvNameMain,
            tvUsernameMain,
            tvFollowingMain,
            tvFollowersMain,
            tvRepositoryMain,
            tvBioMain,
            tvLocationMain,
            tvCompanyMain,
            tvLinkMain;

    TabLayout tabLayoutDetailMain;
    ViewPager viewPagerDetailMain;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    DetailProfileViewModel profileViewModel;
    MainResponse mainResponse;
    String username;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main);

        setInit();

        setParcelableData();

        setWindowNotificationBg();

        setGetSupportActionBar();

        setDetailMainPagerAdapter();
        
        setViewModel();
    }

    private void setInit() {

        mainResponse = getIntent().getParcelableExtra(EXTRA_DETAIL_PROFILE_MAIN);

        toolbarDetailMain = findViewById(R.id.toolbarDetailMain);
        imgMain = findViewById(R.id.imgMain);
        tvNameMain = findViewById(R.id.tvNameMain);
        tvUsernameMain = findViewById(R.id.tvUsernameMain);
        tvFollowingMain = findViewById(R.id.tvFollowingMain);
        tvFollowersMain = findViewById(R.id.tvFollowersMain);
        tvRepositoryMain = findViewById(R.id.tvRepositoryMain);
        tvBioMain = findViewById(R.id.tvBioMain);
        tvLocationMain = findViewById(R.id.tvLocationMain);
        tvCompanyMain = findViewById(R.id.tvCompanyMain);
        tvLinkMain = findViewById(R.id.tvLinkMain);

        tabLayoutDetailMain = findViewById(R.id.tabLayoutDetailMain);
        viewPagerDetailMain = findViewById(R.id.viewPagerDetailMain);

        errorLayout = findViewById(R.id.errorLayout);
        imgErrorImage = findViewById(R.id.imgErrorImage);
        tvErrorTitle = findViewById(R.id.tvErrorTitle);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
    }

    private void setParcelableData() {
        username = mainResponse.getLogin();
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
        setSupportActionBar(toolbarDetailMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + mainResponse.getLogin() + "</font>")));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    //----Ends-----

    private void setDetailMainPagerAdapter() {
        DetailMainPagerAdapter mainPagerAdapter = new DetailMainPagerAdapter(
                getSupportFragmentManager(),
                tabLayoutDetailMain.getTabCount(),
                username
        );
        viewPagerDetailMain.setAdapter(mainPagerAdapter);

        addOnTabSelected();
        viewPagerDetailMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutDetailMain));
    }

    private void setViewModel() {

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
                        String bio = detailProfileResponse.getBio();
                        String location = detailProfileResponse.getLocation();
                        String company = detailProfileResponse.getCompany();
                        String link = detailProfileResponse.getBlog();

                        Glide.with(DetailMainActivity.this)
                                .asBitmap()
                                .load(photo)
                                .placeholder(R.color.colorPrimaryDark)
                                .into(imgMain);

                        tvNameMain.setText(name);
                        tvUsernameMain.setText(username);
                        tvFollowingMain.setText(String.valueOf(following));
                        tvFollowersMain.setText(String.valueOf(followers));
                        tvRepositoryMain.setText(String.valueOf(repos));

                        tvBioMain.setText(bio);
                        tvBioMain.setMarqueeRepeatLimit(5);
                        tvBioMain.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        tvBioMain.setSelected(true);

                        tvLocationMain.setText(location);
                        tvCompanyMain.setText(company);
                        tvLinkMain.setText(link);

                    } else {
                        Log.i("onChangedFailed", "onChanged: ");
                    }
                }
            });
        } else {
            showErrorMessage(R.drawable.ic_no_connection, R.string.tvOops, R.string.tvCheckYourConnection);
        }
    }

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
        tabLayoutDetailMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerDetailMain.setCurrentItem(tab.getPosition());
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