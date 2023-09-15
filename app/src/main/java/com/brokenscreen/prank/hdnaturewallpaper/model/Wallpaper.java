package com.brokenscreen.prank.hdnaturewallpaper.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wallpaper implements Parcelable {
    private String url;


    public Wallpaper() {

    }

    public Wallpaper(String url) {

        this.url = url;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(url);
    }

    public static final Creator<Wallpaper> CREATOR = new Creator<Wallpaper>() {
        public Wallpaper createFromParcel(Parcel in) {
            return new Wallpaper(in);
        }

        public Wallpaper[] newArray(int size) {
            return new Wallpaper[size];
        }
    };

    private Wallpaper(Parcel in) {
        url = in.readString();
    }

}
