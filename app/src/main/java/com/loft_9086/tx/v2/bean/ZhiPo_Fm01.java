package com.loft_9086.tx.v2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class ZhiPo_Fm01 extends BmobObject {
    public String picUrl;
    public String pic;
    public String title;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ZhiPo_Fm01{" +
                "picUrl='" + picUrl + '\'' +
                ", pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
