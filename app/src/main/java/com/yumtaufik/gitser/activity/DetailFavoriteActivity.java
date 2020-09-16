package com.yumtaufik.gitser.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.api.Api;
import com.yumtaufik.gitser.database.GitserHelper;
import com.yumtaufik.gitser.helper.Utils;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class DetailFavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_FAVORITE = "com.yumtaufik.gitser.activity.EXTRA_FAVORITE";
    public static final String EXTRA_POSITION = "com.yumtaufik.gitser.activity.EXTRA_POSITION";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;

    private DetailProfileResponse profileResponse;
    
    private int position;
    private GitserHelper gitserHelper;

    Toolbar toolbarDetailFav;

    ImageView imgFav;

    TextView tvNameFav,
            tvUsernameFav,
            tvFollowingFav,
            tvFollowersFav,
            tvRepositoryFav,
            tvLocationFav,
            tvCompanyFav,
            tvLinkFav;

    Button btnDeleteFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);

        setInit();

        setWindowNotificationBg();

        setGetSupportActionBar();

        setDataPosition();

        setInitOnClick();
    }

    private void setInit() {

        profileResponse = getIntent().getParcelableExtra(EXTRA_FAVORITE);

        gitserHelper = GitserHelper.getInstance(getApplicationContext());

        toolbarDetailFav = findViewById(R.id.toolbarDetailFav);

        imgFav = findViewById(R.id.imgFav);

        tvNameFav = findViewById(R.id.tvNameFav);
        tvUsernameFav = findViewById(R.id.tvUsernameFav);
        tvFollowingFav = findViewById(R.id.tvFollowingFav);
        tvFollowersFav = findViewById(R.id.tvFollowersFav);
        tvRepositoryFav = findViewById(R.id.tvRepositoryFav);
        tvLocationFav = findViewById(R.id.tvLocationFav);
        tvCompanyFav = findViewById(R.id.tvCompanyFav);
        tvLinkFav = findViewById(R.id.tvLinkFav);

        btnDeleteFavorite = findViewById(R.id.btnDeleteFavorite);
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
        setSupportActionBar(toolbarDetailFav);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + profileResponse.getLogin() + "</font>")));
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

        switch (item.getItemId()) {
            case R.id.nav_share_detail_main:

                String urlShare = Api.URL_GITHUB + profileResponse.getLogin();

                try {

                    String body = "Visit this awesome user " + "\n" + urlShare;

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(shareIntent, "Share with:"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.nav_open_browser_detail_main:

                String urlWeb = Api.URL_GITHUB + profileResponse.getLogin();

                try {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb));
                    startActivity(Intent.createChooser(webIntent, "Open with:"));
                } catch (Exception e) {
                    Toasty.warning(DetailFavoriteActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //----Ends-----

    private void showAlertDialogDelete() {

        String favAvatarUrl = imgFav.getResources().toString().trim();
        String favName = tvNameFav.getText().toString().trim();
        String favUsername = tvUsernameFav.getText().toString().trim();
        String favFollowing = tvFollowingFav.getText().toString().trim();
        String favFollowers = tvFollowersFav.getText().toString().trim();
        String favRepository = tvRepositoryFav.getText().toString().trim();
        String favLocation = tvLocationFav.getText().toString().trim();
        String favCompany = tvCompanyFav.getText().toString().trim();
        String favLink = tvLinkFav.getText().toString().trim();

        profileResponse.setAvatarUrl(favAvatarUrl);
        profileResponse.setName(favName);
        profileResponse.setLogin(favUsername);

        int followingInt = Integer.parseInt(favFollowing);
        profileResponse.setFollowing(followingInt);

        int followersInt = Integer.parseInt(favFollowers);
        profileResponse.setFollowers(followersInt);

        int repositoryInt = Integer.parseInt(favRepository);
        profileResponse.setPublicRepos(repositoryInt);

        profileResponse.setLocation(favLocation);
        profileResponse.setCompany(favCompany);
        profileResponse.setBlog(favLink);

        final Intent intent = new Intent();
        intent.putExtra(EXTRA_FAVORITE, profileResponse);
        intent.putExtra(EXTRA_POSITION, position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.tvDeleteFavorite)
                .setMessage(R.string.tvDeleteFavoriteDesc)
                .setCancelable(false)
                .setNegativeButton(R.string.tvNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.tvYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long favoriteItem = gitserHelper.deleteFavorite(profileResponse.getId());
                        if (favoriteItem > 0) {
                            Intent deleteIntent = new Intent();
                            deleteIntent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, deleteIntent);
                            Toasty.success(DetailFavoriteActivity.this, R.string.tvDeletedFavoriteItemSuccessfully, Toast.LENGTH_SHORT, true).show();
                            finish();
                        } else {
                            Toasty.error(DetailFavoriteActivity.this, R.string.tvDeletedFavoriteItemFailed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setDataPosition() {

        if (profileResponse != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            profileResponse = new DetailProfileResponse();
        }

        if (profileResponse != null) {

            byte[] data = gitserHelper.getBitmapAvatarUrl(profileResponse);

            if (data != null) {

                Bitmap bitmaps = Utils.getImage(data);
                imgFav.setImageBitmap(bitmaps);

            } else {
                Toasty.error(DetailFavoriteActivity.this, R.string.tvFailedToShowAvatar, Toast.LENGTH_SHORT, true).show();
            }

            tvNameFav.setText(profileResponse.getName());
            tvUsernameFav.setText(profileResponse.getLogin());
            tvFollowingFav.setText(String.valueOf(profileResponse.getFollowing()));
            tvFollowersFav.setText(String.valueOf(profileResponse.getFollowers()));
            tvRepositoryFav.setText(String.valueOf(profileResponse.getPublicRepos()));
            tvLocationFav.setText(profileResponse.getLocation());
            tvCompanyFav.setText(profileResponse.getCompany());

            SpannableString spannableLink = new SpannableString(profileResponse.getBio());
            ClickableSpan spanLink = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intentLink = new Intent(Intent.ACTION_VIEW, Uri.parse(profileResponse.getBio()));

                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intentLink, PackageManager.MATCH_DEFAULT_ONLY);

                    boolean isIntentSafe = activities.size() > 0;

                    if (isIntentSafe) {
                        startActivity(Intent.createChooser(intentLink, "Open with"));
                    } else {
                        Toasty.warning(DetailFavoriteActivity.this, R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                    }
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                }
            };

            spannableLink.setSpan(spanLink, 0, profileResponse.getBio().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvLinkFav.setText(spannableLink);
            tvLinkFav.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void setInitOnClick() {
        btnDeleteFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDeleteFavorite) {
            showAlertDialogDelete();
        }
    }
}