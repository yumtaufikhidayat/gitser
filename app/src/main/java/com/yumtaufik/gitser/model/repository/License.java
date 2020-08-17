package com.yumtaufik.gitser.model.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License {
    @Expose
    @SerializedName("node_id")
    private String node_id;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("spdx_id")
    private String spdx_id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("key")
    private String key;

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpdx_id() {
        return spdx_id;
    }

    public void setSpdx_id(String spdx_id) {
        this.spdx_id = spdx_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
