package com.loft_9086.tx.v2.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class ShiShiCai_KaiJiang extends BmobObject {
    public String titile;
    public String get_award_data;
    public int type;
    public String SscKj_History;

    @Override
    public String toString() {
        return "ShiShiCai_KaiJiang{" +
                "titile='" + titile + '\'' +
                ", get_award_data='" + get_award_data + '\'' +
                ", type=" + type +
                ", SscKj_History='" + SscKj_History + '\'' +
                '}';
    }

    public String getSscKj_History() {
        return SscKj_History;
    }

    public void setSscKj_History(String sscKj_History) {
        SscKj_History = sscKj_History;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getGet_award_data() {
        return get_award_data;
    }

    public void setGet_award_data(String get_award_data) {
        this.get_award_data = get_award_data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
