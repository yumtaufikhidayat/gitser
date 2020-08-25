package com.yumtaufik.gitser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yumtaufik.gitser.model.favorite.GitserFavorite;

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

    public ArrayList<GitserFavorite> getAllFavorites() {

        ArrayList<GitserFavorite> gitserFavoriteArrayList = new ArrayList<>();

        Cursor cursor = database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null
                , null,
                _ID + " ASC",
                null
        );
        cursor.moveToFirst();

        GitserFavorite gitserFavorite = new GitserFavorite();;
        if (cursor.getCount() > 0) {
            do {

                gitserFavorite.setFavoriteId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                gitserFavorite.setFavoriteAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_AVATAR_URL)));
                gitserFavorite.setFavoriteName(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_NAME)));
                gitserFavorite.setFavoriteUsername(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_USERNAME)));
                gitserFavorite.setFavoriteFollowing(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_FOLLOWING)));
                gitserFavorite.setFavoriteFollowers(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_FOLLOWERS)));
                gitserFavorite.setFavoriteRepository(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_REPOSITORY)));
                gitserFavorite.setFavoriteLocation(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_LOCATION)));
                gitserFavorite.setFavoriteCompany(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_COMPANY)));
                gitserFavorite.setFavoriteLink(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_LINK)));

                gitserFavoriteArrayList.add(gitserFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return gitserFavoriteArrayList;
    }

    public boolean isFavoriteExist(String name) {

        Cursor cursor;

        String sql = "SELECT * FROM " + DATABASE_TABLE + " WHERE FAVORITE_NAME = '" + name + "'";
        cursor = database.rawQuery(sql, null);

        int count = cursor.getCount();

        cursor.close();

        return count > 0;
    }

    public long insertFavorite(GitserFavorite favorite) {

        ContentValues values = new ContentValues();
        values.put(FAVORITE_AVATAR_URL, favorite.getFavoriteAvatarUrl());
        values.put(FAVORITE_NAME, favorite.getFavoriteName());
        values.put(FAVORITE_USERNAME, favorite.getFavoriteUsername());
        values.put(FAVORITE_FOLLOWING, favorite.getFavoriteFollowing());
        values.put(FAVORITE_FOLLOWERS, favorite.getFavoriteFollowers());
        values.put(FAVORITE_REPOSITORY, favorite.getFavoriteRepository());
        values.put(FAVORITE_LOCATION, favorite.getFavoriteLocation());
        values.put(FAVORITE_COMPANY, favorite.getFavoriteCompany());
        values.put(FAVORITE_LINK, favorite.getFavoriteLink());

        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteFavorite(String id) {
        return database.delete(DATABASE_TABLE,
                _ID + " = '"
                + id + "'",
                null
        );
    }
}
