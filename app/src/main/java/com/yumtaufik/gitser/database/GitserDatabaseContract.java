package com.yumtaufik.gitser.database;

import android.provider.BaseColumns;

 public class GitserDatabaseContract {

     public static String TABLE_NAME = "gitserFavoriteTable";

     public static final class GitserColumns implements BaseColumns {

         public static String FAVORITE_AVATAR_URL = "favoriteAvatarUrl";
         public static String FAVORITE_USERNAME = "favoriteUsername";
         public static String FAVORITE_NAME = "favoriteName";
         public static String FAVORITE_FOLLOWING = "favoriteFollowing";
         public static String FAVORITE_FOLLOWERS = "favoriteFollowers";
         public static String FAVORITE_REPOSITORY = "favoriteRepository";
         public static String FAVORITE_LOCATION = "favoriteLocation";
         public static String FAVORITE_COMPANY = "favoriteCompany";
         public static String FAVORITE_LINK = "favoriteLink";
     }
}
