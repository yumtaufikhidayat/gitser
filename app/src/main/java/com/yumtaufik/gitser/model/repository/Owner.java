package com.yumtaufik.gitser.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {
    @Expose
    @SerializedName("site_admin")
    private boolean site_admin;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("received_events_url")
    private String received_events_url;
    @Expose
    @SerializedName("events_url")
    private String events_url;
    @Expose
    @SerializedName("repos_url")
    private String repos_url;
    @Expose
    @SerializedName("organizations_url")
    private String organizations_url;
    @Expose
    @SerializedName("subscriptions_url")
    private String subscriptions_url;
    @Expose
    @SerializedName("starred_url")
    private String starred_url;
    @Expose
    @SerializedName("gists_url")
    private String gists_url;
    @Expose
    @SerializedName("following_url")
    private String following_url;
    @Expose
    @SerializedName("followers_url")
    private String followers_url;
    @Expose
    @SerializedName("html_url")
    private String html_url;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("gravatar_id")
    private String gravatar_id;
    @Expose
    @SerializedName("avatar_url")
    private String avatar_url;
    @Expose
    @SerializedName("node_id")
    private String node_id;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("login")
    private String login;

    public boolean isSite_admin() {
        return site_admin;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
