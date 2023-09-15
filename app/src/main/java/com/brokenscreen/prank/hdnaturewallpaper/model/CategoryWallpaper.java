package com.brokenscreen.prank.hdnaturewallpaper.model;

public class CategoryWallpaper {
    private String url,item_name;

    public CategoryWallpaper(String url, String item_name) {
        this.url = url;

        this.item_name = item_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
