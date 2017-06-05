package com.loft_9086.tx.v2.bean;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class OpenBean {

    public int status;
    public MsgBean msg;
    public String url;

    public static class MsgBean {
        public String id;
        public String mcih;
        public String name;
        public int open;
        public String links;
    }
}
