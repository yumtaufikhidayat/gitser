package com.yumtaufik.gitser.helper;

import android.database.Cursor;

import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.util.ArrayList;
import java.util.Arrays;

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

public class MappingHelper {

    public static ArrayList<DetailProfileResponse> mapCursorToArrayList(Cursor cursor) {

        ArrayList<DetailProfileResponse> profileResponsesList = new ArrayList<>();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String avatarUrl = Arrays.toString(cursor.getBlob(cursor.getColumnIndexOrThrow(FAVORITE_AVATAR_URL)));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_NAME));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_USERNAME));
            int following = cursor.getInt(cursor.getColumnIndexOrThrow(FAVORITE_FOLLOWING));
            int followers = cursor.getInt(cursor.getColumnIndexOrThrow(FAVORITE_FOLLOWERS));
            int repos = cursor.getInt(cursor.getColumnIndexOrThrow(FAVORITE_REPOSITORY));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_LOCATION));
            String company = cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_COMPANY));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE_LINK));

            profileResponsesList.add(new DetailProfileResponse(
                    id,
                    avatarUrl,
                    name,
                    username,
                    following,
                    followers,
                    repos,
                    location,
                    company,
                    link));
        }

        return profileResponsesList;
    }
}
