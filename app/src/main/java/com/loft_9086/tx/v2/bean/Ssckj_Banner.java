package com.loft_9086.tx.v2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/3 0003.
 */

public class Ssckj_Banner extends BmobObject {

    public String title;
    public String pic;
    public String picUrl;

    @Override
    public String toString() {
        return "Ssckj_Banner{" +
                "title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
