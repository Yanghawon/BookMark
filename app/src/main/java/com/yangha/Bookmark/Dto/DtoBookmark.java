package com.yangha.Bookmark.Dto;

/**
 * Created by Yangha on 2017-02-27.
 */

public class DtoBookmark {

    private int index;
    private Double longitude;
    private Double latitude;
    private String title;
    private String image;
    private int category;
    private String content;
    private float rating;
    private String remark;
    private String date;
    private int count;

    public DtoBookmark(int category, String content, int count, String date, String image, int index, Double latitude, Double longitude, float rating, String remark, String title) {
        this.category = category;
        this.content = content;
        this.count = count;
        this.date = date;
        this.image = image;
        this.index = index;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.remark = remark;
        this.title = title;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
