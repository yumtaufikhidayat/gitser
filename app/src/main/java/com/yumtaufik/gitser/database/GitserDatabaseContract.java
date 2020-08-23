package com.yumtaufik.gitser.database;

import android.provider.BaseColumns;

 public class GitserDatabaseContract {

    static String TABLE_NAME = "favoriteTable";

     static final class GitserColumns implements BaseColumns {

         static String FAVORITE_AVATAR_URL = "favoriteAvatarUrl";
         static String FAVORITE_USERNAME = "favoriteUsername";
         static String FAVORITE_NAME = "favoriteName";
         static String FAVORITE_FOLLOWING = "favorite_following";
         static String FAVORITE_FOLLOWERS = "favorite_followers";
         static String FAVORITE_REPOSITORY = "favoriteRepository";
         static String FAVORITE_LOCATION = "favoritelocation";
         static String FAVORITE_COMPANY = "favorite_company";
         static String FAVORITE_LINK = "favorite_link";
     }
}
