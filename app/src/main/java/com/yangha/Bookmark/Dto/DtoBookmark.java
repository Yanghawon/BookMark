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
    private double distance;
    private long dateGap;

    public DtoBookmark() {
    }

    public int getCount() { return count; }

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDateGap(long dateGap) {
        this.dateGap = dateGap;
    }

    public long getDateGap() {
        return dateGap;
    }
}
