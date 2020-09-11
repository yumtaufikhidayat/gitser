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
import com.yumtaufik.gitser.adapter.custom.detailmain.DetailMainPagerAdapter;
import com.yumtaufik.gitser.api.Api;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;
import com.yumtaufik.gitser.model.main.MainResponse;
import com.yumtaufik.gitser.viewmodel.detail.DetailProfileViewModel;

import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class DetailMainActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL_PROFILE_MAIN = "com.yumtaufik.gitser.activity.EXTRA_DETAIL_PROFILE_MAIN";

    Toolbar toolbarDetailMain;

    ImageView imgMain;
    TextView tvNameMain,
            tvUsernameMain,
            tvFollowingMain,
            tvFollowersMain,
            tvRepositoryMain,
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
    String url = Api.URL_GITHUB;
    
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.nav_share_detail_main:
                try {

                    url = url + mainResponse.getLogin();

                    String body = "Visit this awesome user " + "\n" + url;

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plan");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(shareIntent, "Share with:"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.nav_open_browser_detail_main:

                url = url + mainResponse.getLogin();

                try {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(Intent.createChooser(webIntent, "Open with:"));
                } catch (Exception e) {
                    Toasty.warning(DetailMainActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();
                }

                break;
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
                        String location = detailProfileResponse.getLocation();
                        String company = detailProfileResponse.getCompany();
                        final String link = detailProfileResponse.getBlog();

                        Glide.with(DetailMainActivity.this)
                                .asBitmap()
                                .load(photo)
                                .placeholder(R.color.colorPrimaryDark)
                                .into(imgMain);

                        tvNameMain.setText(name);
                        tvUsernameMain.setText(username);

                        tvFollowingMain.setText(String.valueOf(following));
                        String followingStr = tvFollowingMain.getText().toString().trim();
                        Integer followingInt = Integer.parseInt(followingStr);
                        String followingStrNew = String.format(Locale.US, "%,d", followingInt).replace(',', '.');
                        tvFollowersMain.setText(followingStrNew);

                        tvFollowersMain.setText(String.valueOf(followers));
                        String followersStr = tvFollowersMain.getText().toString().trim();
                        Integer followersInt = Integer.parseInt(followersStr);
                        String followersStrNew = String.format(Locale.US, "%,d", followersInt).replace(',', '.');
                        tvFollowersMain.setText(followersStrNew);

                        tvRepositoryMain.setText(String.valueOf(repos));
                        String reposStr = tvRepositoryMain.getText().toString().trim();
                        Integer reposInt = Integer.parseInt(reposStr);
                        String reposStrNew = String.format(Locale.US, "%,d", reposInt).replace(',', '.');
                        tvRepositoryMain.setText(reposStrNew);

                        tvLocationMain.setText(location);
                        tvCompanyMain.setText(company);

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
                                    Toasty.warning(DetailMainActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.BLUE);
                            }
                        };

                        spannableLink.setSpan(spanLink, 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvLinkMain.setText(spannableLink);
                        tvLinkMain.setMovementMethod(LinkMovementMethod.getInstance());

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