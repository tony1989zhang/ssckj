package com.loft_9086.tx.v2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class ZhiPo_Fm02 extends BmobObject {
    public String url;
    public String jianjie;
    public String title;
    public String pic;

    @Override
    public String toString() {
        return "ZhiPo_Fm02{" +
                "url='" + url + '\'' +
                ", jianjie='" + jianjie + '\'' +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
