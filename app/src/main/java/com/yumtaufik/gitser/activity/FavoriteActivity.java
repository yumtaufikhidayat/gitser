package com.yumtaufik.gitser.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.favorite.GitserFavoriteAdapter;
import com.yumtaufik.gitser.database.GitserHelper;
import com.yumtaufik.gitser.helper.LoadProfileCallback;
import com.yumtaufik.gitser.helper.MappingHelper;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadProfileCallback{

    public static final String EXTRA_STATE = "com.yumtaufik.gitser.activity.EXTRA_STATE";

    Toolbar toolbarFavorite;

    GitserFavoriteAdapter adapter;
    RecyclerView rvFavorite;
    GitserHelper gitserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setInit();

        setWindowNotificationBg();

        setGetSupportActionBar();

        if (savedInstanceState == null) {
            new LoadProfileAsync(gitserHelper, this).execute();
        } else {
            ArrayList<DetailProfileResponse> profileResponseList = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (profileResponseList != null) {
                adapter.setGitserFavoriteList(profileResponseList);
            }
        }

        setRecyclerView();
    }

    private void setInit() {

        gitserHelper = new GitserHelper(FavoriteActivity.this);
        adapter = new GitserFavoriteAdapter();

        toolbarFavorite = findViewById(R.id.toolbarFavorite);

        rvFavorite = findViewById(R.id.rvFavorite);
    }

    //----Method to set notification bar----
    private void setWindowNotificationBg() {

        Window windowNotificationBackground = this.getWindow();
        windowNotificationBackground.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            windowNotificationBackground.getDecorView().setSystemUiVisibility(0);
        }
    }
    //----Ends----

    //----Methods to set action bar----
    private void setGetSupportActionBar() {

        setSupportActionBar(toolbarFavorite);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.tvFavorite) + "</font>")));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    //----Ends-----

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFavorite.setLayoutManager(layoutManager);
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DetailFavoriteActivity.REQUEST_ADD) {
                if (resultCode == DetailFavoriteActivity.RESULT_ADD) {
                    DetailProfileResponse profileResponse = data.getParcelableExtra(DetailFavoriteActivity.EXTRA_FAVORITE);
                    adapter.addItem(profileResponse);
                    rvFavorite.smoothScrollToPosition(adapter.getItemCount());
                }
            } else if (requestCode == DetailFavoriteActivity.REQUEST_UPDATE) {
                if (resultCode == DetailFavoriteActivity.RESULT_UPDATE) {
                    DetailProfileResponse profileResponse = data.getParcelableExtra(DetailFavoriteActivity.EXTRA_FAVORITE);
                    int position = data.getIntExtra(DetailFavoriteActivity.EXTRA_POSITION, 0);
                    adapter.updateItem(position, profileResponse);
                } else if (resultCode == DetailFavoriteActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailFavoriteActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gitserHelper.closeDatabase();
        Log.i("gitserHelperDestroy", "onDestroy: ");
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Do run on ui thread
            }
        });
    }

    @Override
    public void postExecute(ArrayList<DetailProfileResponse> profileResponses) {
        adapter.setGitserFavoriteList(profileResponses);
    }

    private static class LoadProfileAsync extends AsyncTask<Void, Void, ArrayList<DetailProfileResponse>> {

        private final WeakReference<GitserHelper> gitserHelperWeakReference;
        private final WeakReference<LoadProfileCallback> profileCallback;

        private LoadProfileAsync(GitserHelper gitserHelper, LoadProfileCallback callback) {
            gitserHelperWeakReference = new WeakReference<>(gitserHelper);
            profileCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            profileCallback.get().preExecute();
        }

        @Override
        protected ArrayList<DetailProfileResponse> doInBackground(Void... voids) {
            Cursor dataCursor = gitserHelperWeakReference.get().getFavorites();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<DetailProfileResponse> profileResponses) {
            super.onPostExecute(profileResponses);
            profileCallback.get().postExecute(profileResponses);
        }
    }
}