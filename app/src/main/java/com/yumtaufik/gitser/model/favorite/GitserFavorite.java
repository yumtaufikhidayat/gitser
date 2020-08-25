package com.yumtaufik.gitser.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class GitserFavorite implements Parcelable {

    int favoriteId;
    String favoriteAvatarUrl;
    String favoriteUsername;
    String favoriteName;
    String favoriteFollowing;
    String favoriteFollowers;
    String favoriteRepository;
    String favoriteLocation;
    String favoriteCompany;
    String favoriteLink;

    public GitserFavorite() {
    }

    public GitserFavorite(int favoriteId, String favoriteAvatarUrl, String favoriteUsername, String favoriteName, String favoriteFollowing, String favoriteFollowers, String favoriteRepository, String favoriteLocation, String favoriteCompany, String favoriteLink) {
        this.favoriteId = favoriteId;
        this.favoriteAvatarUrl = favoriteAvatarUrl;
        this.favoriteUsername = favoriteUsername;
        this.favoriteName = favoriteName;
        this.favoriteFollowing = favoriteFollowing;
        this.favoriteFollowers = favoriteFollowers;
        this.favoriteRepository = favoriteRepository;
        this.favoriteLocation = favoriteLocation;
        this.favoriteCompany = favoriteCompany;
        this.favoriteLink = favoriteLink;
    }

    protected GitserFavorite(Parcel in) {
        favoriteId = in.readInt();
        favoriteAvatarUrl = in.readString();
        favoriteUsername = in.readString();
        favoriteName = in.readString();
        favoriteFollowing = in.readString();
        favoriteFollowers = in.readString();
        favoriteRepository = in.readString();
        favoriteLocation = in.readString();
        favoriteCompany = in.readString();
        favoriteLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(favoriteId);
        dest.writeString(favoriteAvatarUrl);
        dest.writeString(favoriteUsername);
        dest.writeString(favoriteName);
        dest.writeString(favoriteFollowing);
        dest.writeString(favoriteFollowers);
        dest.writeString(favoriteRepository);
        dest.writeString(favoriteLocation);
        dest.writeString(favoriteCompany);
        dest.writeString(favoriteLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GitserFavorite> CREATOR = new Creator<GitserFavorite>() {
        @Override
        public GitserFavorite createFromParcel(Parcel in) {
            return new GitserFavorite(in);
        }

        @Override
        public GitserFavorite[] newArray(int size) {
            return new GitserFavorite[size];
        }
    };

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteAvatarUrl() {
        return favoriteAvatarUrl;
    }

    public void setFavoriteAvatarUrl(String favoriteAvatarUrl) {
        this.favoriteAvatarUrl = favoriteAvatarUrl;
    }

    public String getFavoriteUsername() {
        return favoriteUsername;
    }

    public void setFavoriteUsername(String favoriteUsername) {
        this.favoriteUsername = favoriteUsername;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public String getFavoriteFollowing() {
        return favoriteFollowing;
    }

    public void setFavoriteFollowing(String favoriteFollowing) {
        this.favoriteFollowing = favoriteFollowing;
    }

    public String getFavoriteFollowers() {
        return favoriteFollowers;
    }

    public void setFavoriteFollowers(String favoriteFollowers) {
        this.favoriteFollowers = favoriteFollowers;
    }

    public String getFavoriteRepository() {
        return favoriteRepository;
    }

    public void setFavoriteRepository(String favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public String getFavoriteLocation() {
        return favoriteLocation;
    }

    public void setFavoriteLocation(String favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }

    public String getFavoriteCompany() {
        return favoriteCompany;
    }

    public void setFavoriteCompany(String favoriteCompany) {
        this.favoriteCompany = favoriteCompany;
    }

    public String getFavoriteLink() {
        return favoriteLink;
    }

    public void setFavoriteLink(String favoriteLink) {
        this.favoriteLink = favoriteLink;
    }
}
