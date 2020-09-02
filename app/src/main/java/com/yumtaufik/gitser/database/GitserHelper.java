package com.yumtaufik.gitser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import static android.provider.BaseColumns._ID;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_AVATAR_URL;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_COMPANY;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_FOLLOWERS;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_FOLLOWING;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_LINK;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_LOCATION;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_NAME;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_REPOSITORY;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.GitserColumns.FAVORITE_USERNAME;
import static com.yumtaufik.gitser.database.GitserDatabaseContract.TABLE_NAME;

public class GitserHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static GitserDatabaseHelper databaseHelper;
    private static GitserHelper INSTANCE;

    private static SQLiteDatabase database;

    public GitserHelper(Context context) {
        databaseHelper = new GitserDatabaseHelper(context);
    }

    public static GitserHelper getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GitserHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void openDatabase() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        databaseHelper.close();

        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor getFavorites() {

        String sql = "SELECT * FROM " + DATABASE_TABLE;
        database = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(sql, null);
        }

        return cursor;
    }

    public boolean isFavoriteExist(String name) {

        Cursor cursor;

        String sql = " SELECT * FROM " + DATABASE_TABLE + " WHERE " + FAVORITE_NAME + " = '" + name + "'";
        cursor = database.rawQuery(sql, null);

        int count = cursor.getCount();

        cursor.close();

        return count > 0;
    }

    public long insertFavorite(DetailProfileResponse profileResponse) {

        ContentValues values = new ContentValues();
        values.put(FAVORITE_AVATAR_URL, profileResponse.getAvatarUrl());
        values.put(FAVORITE_NAME, profileResponse.getName());
        values.put(FAVORITE_USERNAME, profileResponse.getLogin());
        values.put(FAVORITE_FOLLOWING, profileResponse.getFollowing());
        values.put(FAVORITE_FOLLOWERS, profileResponse.getFollowers());
        values.put(FAVORITE_REPOSITORY, profileResponse.getPublicRepos());
        values.put(FAVORITE_LOCATION, profileResponse.getLocation());
        values.put(FAVORITE_COMPANY, profileResponse.getCompany());
        values.put(FAVORITE_LINK, profileResponse.getBlog());

        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteFavorite(int id) {
        return database.delete(DATABASE_TABLE,
                _ID + " = '"
                + id + "'",
                null
        );
    }
}
