package com.yumtaufik.gitser.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.database.GitserHelper;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import es.dmoral.toasty.Toasty;

public class DetailFavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_FAVORITE = "com.yumtaufik.gitser.activity.EXTRA_FAVORITE";
    public static final String EXTRA_POSITION = "com.yumtaufik.gitser.activity.EXTRA_POSITION";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;

    private final int ALERT_DIALOG_DELETE = 20;

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

        setGitserDbHelper();

        setDataPosition();
    }

    private void setInit() {

        profileResponse = getIntent().getParcelableExtra(EXTRA_FAVORITE);

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
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + R.string.tvDetailFavorite + "</font>")));
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

    private void showAlertDialogDelete(int type) {

        final boolean isDialogDelete = type == ALERT_DIALOG_DELETE;

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
                        if (isDialogDelete) {
                            finish();
                        } else {
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
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setGitserDbHelper() {
        gitserHelper = GitserHelper.getInstance(getApplicationContext());
    }

    private void setDataPosition() {

        if (profileResponse != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            profileResponse = new DetailProfileResponse();
        }

        if (profileResponse != null) {

            Glide.with(this)
                    .asBitmap()
                    .load(profileResponse.getAvatarUrl())
                    .placeholder(R.color.colorPrimaryDark)
                    .into(imgFav);

            tvNameFav.setText(profileResponse.getName());
            tvUsernameFav.setText(profileResponse.getLogin());
            tvFollowingFav.setText(profileResponse.getFollowing());
            tvFollowersFav.setText(profileResponse.getFollowers());
            tvRepositoryFav.setText(profileResponse.getPublicRepos());
            tvLocationFav.setText(profileResponse.getLocation());
            tvCompanyFav.setText(profileResponse.getCompany());
            tvLinkFav.setText(profileResponse.getBlog());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDeleteFavorite) {
            showAlertDialogDelete(ALERT_DIALOG_DELETE);
        }
    }
}