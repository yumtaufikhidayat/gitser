package com.yumtaufik.gitser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class GitserHelper {

    private static final String DATABASE_TABLE = GitserDatabaseContract.TABLE_NAME;
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

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null
                ,null,
                _ID + "ASC"
        );
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insertFavorite(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE,
                _ID + " = ?",
                new String[]{
                        id
        });
    }
}
