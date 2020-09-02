package com.yumtaufik.gitser.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GitserDatabaseHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "GitserDB";

    private static String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "%s BLOB NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL,"
            + "%s TEXT NOT NULL)",
            GitserDatabaseContract.TABLE_NAME,
            GitserDatabaseContract.GitserColumns._ID,
            GitserDatabaseContract.GitserColumns.FAVORITE_AVATAR_URL,
            GitserDatabaseContract.GitserColumns.FAVORITE_USERNAME,
            GitserDatabaseContract.GitserColumns.FAVORITE_NAME,
            GitserDatabaseContract.GitserColumns.FAVORITE_FOLLOWING,
            GitserDatabaseContract.GitserColumns.FAVORITE_FOLLOWERS,
            GitserDatabaseContract.GitserColumns.FAVORITE_REPOSITORY,
            GitserDatabaseContract.GitserColumns.FAVORITE_LOCATION,
            GitserDatabaseContract.GitserColumns.FAVORITE_COMPANY,
            GitserDatabaseContract.GitserColumns.FAVORITE_LINK
    );

    public GitserDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GitserDatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
