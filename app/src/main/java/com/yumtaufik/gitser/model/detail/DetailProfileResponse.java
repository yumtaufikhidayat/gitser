package com.yumtaufik.gitser.model.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DetailProfileResponse implements Parcelable {
	@SerializedName("gists_url")
	private String gistsUrl;
	@SerializedName("repos_url")
	private String reposUrl;
	@SerializedName("following_url")
	private String followingUrl;
	@SerializedName("twitter_username")
	private Object twitterUsername;
	@SerializedName("bio")
	private String bio;
	@SerializedName("created_at")
	private String createdAt;
	@SerializedName("login")
	private String login;
	@SerializedName("type")
	private String type;
	@SerializedName("blog")
	private String blog;
	@SerializedName("subscriptions_url")
	private String subscriptionsUrl;
	@SerializedName("updated_at")
	private String updatedAt;
	@SerializedName("site_admin")
	private boolean siteAdmin;
	@SerializedName("company")
	private String company;
	@SerializedName("id")
	private int id;
	@SerializedName("public_repos")
	private int publicRepos;
	@SerializedName("gravatar_id")
	private String gravatarId;
	@SerializedName("email")
	private Object email;
	@SerializedName("organizations_url")
	private String organizationsUrl;
	@SerializedName("hireable")
	private Object hireable;
	@SerializedName("starred_url")
	private String starredUrl;
	@SerializedName("followers_url")
	private String followersUrl;
	@SerializedName("public_gists")
	private int publicGists;
	@SerializedName("url")
	private String url;
	@SerializedName("received_events_url")
	private String receivedEventsUrl;
	@SerializedName("followers")
	private int followers;
	@SerializedName("avatar_url")
	private String avatarUrl;
	@SerializedName("events_url")
	private String eventsUrl;
	@SerializedName("html_url")
	private String htmlUrl;
	@SerializedName("following")
	private int following;
	@SerializedName("name")
	private String name;
	@SerializedName("location")
	private String location;
	@SerializedName("node_id")
	private String nodeId;

	public DetailProfileResponse() {
		//required empty constructor
	}

	protected DetailProfileResponse(Parcel in) {
		gistsUrl = in.readString();
		reposUrl = in.readString();
		followingUrl = in.readString();
		bio = in.readString();
		createdAt = in.readString();
		login = in.readString();
		type = in.readString();
		blog = in.readString();
		subscriptionsUrl = in.readString();
		updatedAt = in.readString();
		siteAdmin = in.readByte() != 0;
		company = in.readString();
		id = in.readInt();
		publicRepos = in.readInt();
		gravatarId = in.readString();
		organizationsUrl = in.readString();
		starredUrl = in.readString();
		followersUrl = in.readString();
		publicGists = in.readInt();
		url = in.readString();
		receivedEventsUrl = in.readString();
		followers = in.readInt();
		avatarUrl = in.readString();
		eventsUrl = in.readString();
		htmlUrl = in.readString();
		following = in.readInt();
		name = in.readString();
		location = in.readString();
		nodeId = in.readString();
	}

	public static final Creator<DetailProfileResponse> CREATOR = new Creator<DetailProfileResponse>() {
		@Override
		public DetailProfileResponse createFromParcel(Parcel in) {
			return new DetailProfileResponse(in);
		}

		@Override
		public DetailProfileResponse[] newArray(int size) {
			return new DetailProfileResponse[size];
		}
	};

	public DetailProfileResponse(int id, String avatarUrl, String name, String username, int following, int followers, int repos, String location, String company, String link) {
		this.id = id;
		this.avatarUrl = avatarUrl;
		this.name = name;
		this.login = username;
		this.following = following;
		this.followers = followers;
		this.publicRepos = repos;
		this.location = location;
		this.company = company;
		this.bio = link;
	}

	public String getGistsUrl() {
		return gistsUrl;
	}

	public void setGistsUrl(String gistsUrl) {
		this.gistsUrl = gistsUrl;
	}

	public String getReposUrl() {
		return reposUrl;
	}

	public void setReposUrl(String reposUrl) {
		this.reposUrl = reposUrl;
	}

	public String getFollowingUrl() {
		return followingUrl;
	}

	public void setFollowingUrl(String followingUrl) {
		this.followingUrl = followingUrl;
	}

	public Object getTwitterUsername() {
		return twitterUsername;
	}

	public void setTwitterUsername(Object twitterUsername) {
		this.twitterUsername = twitterUsername;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getSubscriptionsUrl() {
		return subscriptionsUrl;
	}

	public void setSubscriptionsUrl(String subscriptionsUrl) {
		this.subscriptionsUrl = subscriptionsUrl;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isSiteAdmin() {
		return siteAdmin;
	}

	public void setSiteAdmin(boolean siteAdmin) {
		this.siteAdmin = siteAdmin;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPublicRepos() {
		return publicRepos;
	}

	public void setPublicRepos(int publicRepos) {
		this.publicRepos = publicRepos;
	}

	public String getGravatarId() {
		return gravatarId;
	}

	public void setGravatarId(String gravatarId) {
		this.gravatarId = gravatarId;
	}

	public Object getEmail() {
		return email;
	}

	public void setEmail(Object email) {
		this.email = email;
	}

	public String getOrganizationsUrl() {
		return organizationsUrl;
	}

	public void setOrganizationsUrl(String organizationsUrl) {
		this.organizationsUrl = organizationsUrl;
	}

	public Object getHireable() {
		return hireable;
	}

	public void setHireable(Object hireable) {
		this.hireable = hireable;
	}

	public String getStarredUrl() {
		return starredUrl;
	}

	public void setStarredUrl(String starredUrl) {
		this.starredUrl = starredUrl;
	}

	public String getFollowersUrl() {
		return followersUrl;
	}

	public void setFollowersUrl(String followersUrl) {
		this.followersUrl = followersUrl;
	}

	public int getPublicGists() {
		return publicGists;
	}

	public void setPublicGists(int publicGists) {
		this.publicGists = publicGists;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReceivedEventsUrl() {
		return receivedEventsUrl;
	}

	public void setReceivedEventsUrl(String receivedEventsUrl) {
		this.receivedEventsUrl = receivedEventsUrl;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getEventsUrl() {
		return eventsUrl;
	}

	public void setEventsUrl(String eventsUrl) {
		this.eventsUrl = eventsUrl;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(gistsUrl);
		dest.writeString(reposUrl);
		dest.writeString(followingUrl);
		dest.writeString(bio);
		dest.writeString(createdAt);
		dest.writeString(login);
		dest.writeString(type);
		dest.writeString(blog);
		dest.writeString(subscriptionsUrl);
		dest.writeString(updatedAt);
		dest.writeByte((byte) (siteAdmin ? 1 : 0));
		dest.writeString(company);
		dest.writeInt(id);
		dest.writeInt(publicRepos);
		dest.writeString(gravatarId);
		dest.writeString(organizationsUrl);
		dest.writeString(starredUrl);
		dest.writeString(followersUrl);
		dest.writeInt(publicGists);
		dest.writeString(url);
		dest.writeString(receivedEventsUrl);
		dest.writeInt(followers);
		dest.writeString(avatarUrl);
		dest.writeString(eventsUrl);
		dest.writeString(htmlUrl);
		dest.writeInt(following);
		dest.writeString(name);
		dest.writeString(location);
		dest.writeString(nodeId);
	}
}
