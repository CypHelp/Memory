package com.ab.view.bannerimages;

/**
 * Created by wolf on 2015/10/29.
 * 首页顶部广告
 */
public class AdBean {
    private String id;
    private String title;
    private String picture;
    private String image;
    /**
     * 跳转的url
     */
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
