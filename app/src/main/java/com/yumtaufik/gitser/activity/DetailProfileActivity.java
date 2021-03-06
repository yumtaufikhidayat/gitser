package com.yumtaufik.gitser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.custom.detailprofile.DetailProfilePagerAdapter;
import com.yumtaufik.gitser.api.Api;
import com.yumtaufik.gitser.database.GitserHelper;
import com.yumtaufik.gitser.helper.Utils;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.model.search.SearchItems;
import com.yumtaufik.gitser.viewmodel.detail.DetailProfileViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class DetailProfileActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL_PROFILE = "com.yumtaufik.gitser.activity.EXTRA_DETAIL_PROFILE";
    public static final int RESULT_ADD = 101;
    public static final String EXTRA_PROFILE = "com.yumtaufik.gitser.activity.EXTRA_PROFILE";
    public static final String EXTRA_POSITION = "com.yumtaufik.gitser.activity.EXTRA_POSITION";

    Toolbar toolbarDetail;
    
    ImageView imgUserProfile;
    TextView tvNameUserProfile,
            tvUsernameUserProfile,
            tvFollowingUserProfile,
            tvFollowersUserProfile,
            tvRepositoryUserProfile,
            tvLocationUserProfile,
            tvCompanyUserProfile,
            tvLinkUserProfile;

    TabLayout tabLayoutDetailProfile;
    ViewPager viewPagerDetailProfile;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    DetailProfileViewModel profileViewModel;
    SearchItems searchItems;

    DetailProfileResponse profileResponse;

    String username;
    String url = Api.URL_GITHUB;
    int position;

    GitserHelper gitserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);

        setInit();

        setParcelableData();

        setWindowNotificationBg();

        setGetSupportActionBar();

        setDetailProfilePagerAdapter();

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
        tvLocationUserProfile = findViewById(R.id.tvLocationUserProfile);
        tvCompanyUserProfile = findViewById(R.id.tvCompanyUserProfile);
        tvLinkUserProfile = findViewById(R.id.tvLinkUserProfile);

        tabLayoutDetailProfile = findViewById(R.id.tabLayoutDetailProfile);
        viewPagerDetailProfile = findViewById(R.id.viewPagerDetailProfile);

        errorLayout = findViewById(R.id.errorLayout);
        imgErrorImage = findViewById(R.id.imgErrorImage);
        tvErrorTitle = findViewById(R.id.tvErrorTitle);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        gitserHelper = GitserHelper.getInstance(getApplicationContext());
        gitserHelper.openDatabase();
    }

    private void setParcelableData() {
        username = searchItems.getLogin();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.nav_favorite_profile:

                profileResponse = new DetailProfileResponse();

                String avatarUrl = imgUserProfile.getResources().toString().trim();
                String name = tvNameUserProfile.getText().toString().trim();
                String username = tvUsernameUserProfile.getText().toString().trim();
                String following = tvFollowingUserProfile.getText().toString().trim();
                String followers = tvFollowersUserProfile.getText().toString().trim();
                String repository = tvRepositoryUserProfile.getText().toString().trim();
                String location = tvLocationUserProfile.getText().toString().trim();
                String company = tvCompanyUserProfile.getText().toString().trim();
                String link = tvLinkUserProfile.getText().toString().trim();

                profileResponse.setAvatarUrl(avatarUrl);
                profileResponse.setName(name);
                profileResponse.setLogin(username);

                int followingInt = Integer.parseInt(following);
                profileResponse.setFollowing(followingInt);

                int followersInt = Integer.parseInt(followers);
                profileResponse.setFollowers(followersInt);

                int repositoryInt = Integer.parseInt(repository);
                profileResponse.setPublicRepos(repositoryInt);

                profileResponse.setLocation(location);
                profileResponse.setCompany(company);
                profileResponse.setBlog(link);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_PROFILE, profileResponse);
                intent.putExtra(EXTRA_POSITION, position);

                boolean update = gitserHelper.isFavoriteExist(profileResponse.getName());

                if (update) {
                    Toasty.warning(DetailProfileActivity.this, R.string.tvFavoriteExist, Toast.LENGTH_SHORT, true).show();
                } else {

                    Bitmap bitmap = ((BitmapDrawable) imgUserProfile.getDrawable()).getBitmap();

                    long insert = gitserHelper.insertFavorite(profileResponse, Utils.getBytes(bitmap));

                    if (insert > 0) {
                        profileResponse.setId((int) insert);
                        setResult(RESULT_ADD);
                        Toasty.success(DetailProfileActivity.this, R.string.tvInsertFavoriteSuccessfully, Toast.LENGTH_SHORT, true).show();
                        Log.i("insertSuccess", "onCheckedChanged: ");
                        finish();
                    } else {
                        Toasty.error(DetailProfileActivity.this, R.string.tvInsertFavoriteFailed, Toast.LENGTH_SHORT, true).show();
                        Log.i("insertFailed", "onCheckedChanged: ");
                    }
                }

                break;

            case R.id.nav_share_detail_profile:

                url = url + searchItems.getLogin();

                try {

                    String body = "Visit this awesome user " + "\n" + url;

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(shareIntent, "Share with:"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.nav_open_browser_detail_profile:

                url = url + searchItems.getLogin();

                try {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(Intent.createChooser(webIntent, "Open with:"));
                } catch (Exception e) {
                    Toasty.warning(DetailProfileActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //----Ends-----

    private void setDetailProfilePagerAdapter() {
        DetailProfilePagerAdapter profilePagerAdapter = new DetailProfilePagerAdapter(
                getSupportFragmentManager(),
                tabLayoutDetailProfile.getTabCount(),
                username
        );
        viewPagerDetailProfile.setAdapter(profilePagerAdapter);

        addOnTabSelected();
        viewPagerDetailProfile.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutDetailProfile));
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
                        String location = detailProfileResponse.getLocation();
                        String company = detailProfileResponse.getCompany();
                        final String link = detailProfileResponse.getBlog();

                        Glide.with(DetailProfileActivity.this)
                                .asBitmap()
                                .load(photo)
                                .placeholder(R.color.colorPrimaryDark)
                                .into(imgUserProfile);

                        tvNameUserProfile.setText(name);
                        tvUsernameUserProfile.setText(username);
                        tvFollowingUserProfile.setText(String.valueOf(following));
                        tvFollowersUserProfile.setText(String.valueOf(followers));
                        tvRepositoryUserProfile.setText(String.valueOf(repos));
                        tvLocationUserProfile.setText(location);
                        tvCompanyUserProfile.setText(company);

                        SpannableString spannableLink = new SpannableString(link);
                        ClickableSpan spanLink = new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                Intent intentLink = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                                PackageManager packageManager = getPackageManager();
                                List<ResolveInfo> activities = packageManager.queryIntentActivities(intentLink, PackageManager.MATCH_DEFAULT_ONLY);

                                boolean isIntentSafe = activities.size() > 0;

                                if (isIntentSafe) {
                                    startActivity(Intent.createChooser(intentLink, "Open with"));
                                } else {
                                    Toasty.warning(DetailProfileActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);
                            }
                        };

                        spannableLink.setSpan(spanLink, 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvLinkUserProfile.setText(spannableLink);
                        tvLinkUserProfile.setMovementMethod(LinkMovementMethod.getInstance());

                    } else {
                        Log.i("onChangedFailed", "onChanged: ");
                    }
                }
            });
        } else {
            showErrorMessage();
            errorLayout.setVisibility(View.GONE);
        }
    }

    //----Method to show error connection----
    private void showErrorMessage() {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        imgErrorImage.setImageResource(R.drawable.ic_no_connection);
        tvErrorTitle.setText(R.string.tvOops);
        tvErrorMessage.setText(R.string.tvCheckYourConnection);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gitserHelper.closeDatabase();
    }
}