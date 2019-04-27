package com.example.hp.livewallpaper.model;

public class ModelItem {
    public String imageUrl;
    public String categoryId;
    public long viewCount;
    public ModelItem(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ModelItem(String categoryId,String imageUrl) {

        this.categoryId = categoryId;
        this.imageUrl=imageUrl;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}

