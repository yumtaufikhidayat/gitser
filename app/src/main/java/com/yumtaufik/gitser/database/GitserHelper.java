package com.yumtaufik.gitser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.util.ArrayList;

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

    public static ArrayList<DetailProfileResponse> getAllFavorites() {

        ArrayList<DetailProfileResponse> profileResponseList = new ArrayList<>();

        Cursor cursor = databaseHelper.getReadableDatabase().query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null
                , null,
                _ID + " DESC",
                null
        );
        cursor.moveToFirst();

        DetailProfileResponse profileResponse;
        if (cursor.getCount() > 0) {
            do {
                profileResponse = new DetailProfileResponse();
                profileResponse.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                profileResponse.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_AVATAR_URL)));
                profileResponse.setName(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_NAME)));
                profileResponse.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_USERNAME)));
                profileResponse.setFollowing(cursor.getInt(cursor.getColumnIndexOrThrow(FAVORITE_FOLLOWING)));
                profileResponse.setFollowers(cursor.getInt(cursor.getColumnIndexOrThrow(FAVORITE_FOLLOWERS)));
                profileResponse.setPublicRepos(cursor.getInt(cursor.getColumnIndexOrThrow(FAVORITE_REPOSITORY)));
                profileResponse.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_LOCATION)));
                profileResponse.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_COMPANY)));
                profileResponse.setBlog(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_LINK)));

                profileResponseList.add(profileResponse);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return profileResponseList;
    }

    public Cursor getAllFavorite() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null
                , null,
                _ID + " DESC");
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
