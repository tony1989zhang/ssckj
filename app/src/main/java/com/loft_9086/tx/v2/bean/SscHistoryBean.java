package com.loft_9086.tx.v2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public class SscHistoryBean implements Serializable {

    public int state;
    public Object error;
    public List<ItemsBean> items;

    public static class ItemsBean {
        public String date;
        public String time;
        public String period;
        public String result;

        @Override
        public String toString() {
            return "ItemsBean{" +
                    "date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    ", period='" + period + '\'' +
                    ", result='" + result + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SscHistoryBean{" +
                "state=" + state +
                ", error=" + error +
                ", items=" + items +
                '}';
    }
}
