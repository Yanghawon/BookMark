package com.yangha.Bookmark.Dto;

/**
 * Created by Yangha on 2017-02-27.
 */

public class DtoBookmark {
    private Double doubleitude;
    private Double latitude;
    private String title;
    private String image;
    private int category;
    private String content;
    private float rating;
    private String remark;

    public DtoBookmark(int category, String content, String image, double latitude, double doubleitude, float rating, String remark, String title) {
        this.category = category;
        this.content = content;
        this.image = image;
        this.latitude = latitude;
        this.doubleitude = doubleitude;
        this.rating = rating;
        this.remark = remark;
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getdoubleitude() {
        return doubleitude;
    }

    public void setdoubleitude(double doubleitude) {
        this.doubleitude = doubleitude;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
