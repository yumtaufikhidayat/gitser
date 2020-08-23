package com.yumtaufik.gitser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.main.MainAdapter;
import com.yumtaufik.gitser.helper.LoadingDialog;
import com.yumtaufik.gitser.model.main.MainResponse;
import com.yumtaufik.gitser.viewmodel.main.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbarMain;
    MainAdapter adapter;
    SwipeRefreshLayout swipeRefreshMain;
    RecyclerView rvMain;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();

        setWindowNotificationBg();

        setGetSupportActionBar();

        setViewModel();

        swipeRefreshLayoutOnRefreshListener();
    }

    private void setInit() {
        toolbarMain = findViewById(R.id.toolbarMain);
        swipeRefreshMain = findViewById(R.id.swipeRefreshMain);
        rvMain = findViewById(R.id.rvMain);

        errorLayout = findViewById(R.id.errorLayout);
        imgErrorImage = findViewById(R.id.imgErrorImage);
        tvErrorTitle = findViewById(R.id.tvErrorTitle);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        loadingDialog = new LoadingDialog(this);
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
        setSupportActionBar(toolbarMain);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_search_main:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;

            case R.id.nav_favorite:
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                break;

            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    //----Ends----

    private void setViewModel() {

        errorLayout.setVisibility(View.GONE);

        swipeRefreshMain.setRefreshing(true);
        loadingDialog.startLoadingDialog();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvMain.setLayoutManager(layoutManager);
        rvMain.setHasFixedSize(true);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.setNestedScrollingEnabled(false);

        adapter = new MainAdapter();
        rvMain.setAdapter(adapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (isNetworkAvailable()) {
            mainViewModel.getAllUsers().observe(this, new Observer<List<MainResponse>>() {
                @Override
                public void onChanged(List<MainResponse> mainResponses) {
                    if (mainResponses.size() > 0) {
                        adapter.setDataMain(mainResponses);
                        swipeRefreshMain.setRefreshing(false);
                        loadingDialog.dismissLoadingDialog();
                    } else {
                        swipeRefreshMain.setRefreshing(false);
                        loadingDialog.dismissLoadingDialog();
                        showErrorMessage(R.drawable.no_result, R.string.tvNoResult, R.string.tvNoResultDesc);
                    }
                    errorLayout.setVisibility(View.GONE);
                }
            });
        } else {
            showErrorMessage(R.drawable.ic_no_connection, R.string.tvOops, R.string.tvCheckYourConnection);
            swipeRefreshMain.setRefreshing(false);
            loadingDialog.dismissLoadingDialog();
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

    //----Method to set swipe refresh layout----
    private void swipeRefreshLayoutOnRefreshListener() {
        swipeRefreshMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setViewModel();
            }
        });
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
}