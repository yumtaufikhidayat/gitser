package com.yumtaufik.gitser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.custom.profile.ProfilePagerAdapter;
import com.yumtaufik.gitser.bottomsheet.ProfileInfoBottomSheet;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.viewmodel.detail.DetailProfileViewModel;

import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbarProfile;

    ImageView imgProfile;

    TextView tvName,
            tvUsername,
            tvFollowing,
            tvFollowers,
            tvRepository,
            tvLocation,
            tvCompany,
            tvLink;

    TabLayout tabLayoutProfile;
    ViewPager viewPagerProfile;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    DetailProfileViewModel profileViewModel;

    String username = "yumtaufikhidayat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setInit();

        setWindowNotificationBg();

        setGetSupportActionBar();

        setProfilePagerAdapter();

        setViewModel();
    }

    private void setInit() {

        toolbarProfile = findViewById(R.id.toolbarProfile);

        imgProfile = findViewById(R.id.imgProfile);

        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvUsername);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvFollowers = findViewById(R.id.tvFollowers);
        tvRepository = findViewById(R.id.tvRepository);
        tvLocation = findViewById(R.id.tvLocation);
        tvCompany = findViewById(R.id.tvCompany);
        tvLink = findViewById(R.id.tvLink);

        tabLayoutProfile = findViewById(R.id.tabLayoutProfile);
        viewPagerProfile = findViewById(R.id.viewPagerProfile);

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

        setSupportActionBar(toolbarProfile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.tvProfile) + "</font>")));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.nav_info:
                ProfileInfoBottomSheet profileInfoBottomSheet = new ProfileInfoBottomSheet();
                profileInfoBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseBottomSheetMenu);
                profileInfoBottomSheet.show(getSupportFragmentManager(), "profileInfoBottomSheet");
                break;

            case R.id.nav_language:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //----Ends-----

    private void setProfilePagerAdapter() {
        ProfilePagerAdapter profilePagerAdapter = new ProfilePagerAdapter(
                getSupportFragmentManager(),
                tabLayoutProfile.getTabCount(),
                username
        );
        viewPagerProfile.setAdapter(profilePagerAdapter);

        addOnTabSelected();
        viewPagerProfile.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutProfile));
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

                        Glide.with(ProfileActivity.this)
                                .asBitmap()
                                .load(photo)
                                .placeholder(R.color.colorPrimaryDark)
                                .into(imgProfile);

                        tvName.setText(name);
                        tvUsername.setText(username);

                        tvFollowing.setText(String.valueOf(following));
                        String followingStr = tvFollowing.getText().toString().trim();
                        Integer followingInt = Integer.parseInt(followingStr);
                        String followingStrNew = String.format(Locale.US, "%,d", followingInt).replace(',', '.');
                        tvFollowing.setText(followingStrNew);

                        tvFollowers.setText(String.valueOf(followers));
                        String followersStr = tvFollowers.getText().toString().trim();
                        Integer followersInt = Integer.parseInt(followersStr);
                        String followersStrNew = String.format(Locale.US, "%,d", followersInt).replace(',', '.');
                        tvFollowers.setText(followersStrNew);

                        tvRepository.setText(String.valueOf(repos));
                        String reposStr = tvRepository.getText().toString().trim();
                        Integer reposInt = Integer.parseInt(reposStr);
                        String reposStrNew = String.format(Locale.US, "%,d", reposInt).replace(',', '.');
                        tvRepository.setText(reposStrNew);

                        tvLocation.setText(location);
                        tvCompany.setText(company);

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
                                    Toasty.warning(ProfileActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);
                            }
                        };

                        spannableLink.setSpan(spanLink, 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvLink.setText(spannableLink);
                        tvLink.setMovementMethod(LinkMovementMethod.getInstance());

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
        tabLayoutProfile.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerProfile.setCurrentItem(tab.getPosition());
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