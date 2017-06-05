package com.loft_9086.tx.v2.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/3 0003.
 */

public class SscBean implements Serializable {


    public int state;
    public ItemsBean items;
    public Object error;

    public static class ItemsBean {
        public long time;
        public CurrentBean current;
        public NextBean next;
        public boolean state;

        public static class CurrentBean {
            public String period;
            public String date;
            public String time;
            public String result;
        }

        public static class NextBean {
            public String period;
            public String date;
            public String time;
            public int interval;
            public int delayinterval;
        }
    }
}
