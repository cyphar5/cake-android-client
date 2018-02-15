package com.waracle.androidtest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by achau on 14-02-2018.
 */

public class Cake implements Parcelable {
    protected Cake(Parcel in) {
        title = in.readString();
        desc = in.readString();
        img_url = in.readString();
    }

    public Cake(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(img_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cake> CREATOR = new Creator<Cake>() {
        @Override
        public Cake createFromParcel(Parcel in) {
            return new Cake(in);
        }

        @Override
        public Cake[] newArray(int size) {
            return new Cake[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    private String title = "" ;
    private String desc = "" ;
    private String img_url = "" ;
}
