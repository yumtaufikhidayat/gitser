package com.yumtaufik.gitser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.adapter.search.SearchAdapter;
import com.yumtaufik.gitser.model.search.SearchItems;
import com.yumtaufik.gitser.viewmodel.search.SearchViewModel;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbarSearchMain;
    SwipeRefreshLayout swipeRefreshSearch;
    RecyclerView rvSearchUser;
    SearchAdapter adapter;
    SearchViewModel viewModel;

    ConstraintLayout errorLayout;
    ImageView imgErrorImage;
    TextView tvErrorTitle, tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setInit();

        setWindowNotificationBg();

        setGetSupportActionBar();

        swipeRefreshLayoutOnRefreshListener();
    }

    private void setInit() {
        toolbarSearchMain = findViewById(R.id.toolbarSearchMain);
        swipeRefreshSearch = findViewById(R.id.swipeRefreshSearch);
        rvSearchUser = findViewById(R.id.rvSearchUser);

        errorLayout = findViewById(R.id.errorLayout);
        imgErrorImage = findViewById(R.id.imgErrorImage);
        tvErrorTitle = findViewById(R.id.tvErrorTitle);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
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
        setSupportActionBar(toolbarSearchMain);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.tvSearch) + "</font>")));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.nav_favorite:
                //To favorite activity
                break;

            case R.id.nav_profile:
                startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.nav_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Cari username...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                setSearchUser(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    //----Ends----

    private void setSearchUser(String query) {

        errorLayout.setVisibility(View.GONE);

        swipeRefreshSearch.setRefreshing(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        
        rvSearchUser.setLayoutManager(layoutManager);
        rvSearchUser.setHasFixedSize(true);
        rvSearchUser.setItemAnimator(new DefaultItemAnimator());
        rvSearchUser.setNestedScrollingEnabled(false);

        adapter = new SearchAdapter();
        rvSearchUser.setAdapter(adapter);

        if (isNetworkAvailable()) {

            if (!query.isEmpty()) {
                viewModel.getSearchByUsername(query).observe(SearchActivity.this, new Observer<List<SearchItems>>() {
                    @Override
                    public void onChanged(List<SearchItems> searchItems) {
                        if (searchItems.size() > 0) {
                            adapter.setDataItems(searchItems);
                            swipeRefreshSearch.setRefreshing(false);
                        } else {
                            swipeRefreshSearch.setRefreshing(false);
                            showErrorMessage(R.drawable.no_result, R.string.tvNoResult, R.string.tvNoResultDesc);
                        }
                        errorLayout.setVisibility(View.GONE);
                    }
                });
            }
        } else {
            showErrorMessage(R.drawable.ic_no_connection, R.string.tvOops, R.string.tvCheckYourConnection);
            swipeRefreshSearch.setRefreshing(false);
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
        swipeRefreshSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshSearch.setRefreshing(false);
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