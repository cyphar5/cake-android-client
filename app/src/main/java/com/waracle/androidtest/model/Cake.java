package com.waracle.androidtest.model;

/**
 * Created by achau on 14-02-2018.
 */

public class Cake {
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
