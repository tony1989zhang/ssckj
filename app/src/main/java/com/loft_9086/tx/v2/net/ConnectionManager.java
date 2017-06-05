package com.loft_9086.tx.v2.net;

import android.content.Context;

import com.android.volley.Request;

/**
 * Created by Administrator on 2016/1/2.
 */
public class ConnectionManager {

    private static final String PATH_SEND_SMS = "/medicalmanager/app/common/sendSms.do";
    private static ConnectionManager mConnectionManager;
    public static String URL_NEWS_LOTTERY = "http://888.shof789.com/Home/Outs/article/type/";

    private static final String OPEN_URL = "http://888.shof789.com/Home/Outs/index/mchid/591034827b587.html";
    private static final String SHARE_URL = "http://888.shof789.com/Home/Outs/index/mchid/59103487104e5.html";

    private ConnectionManager() {

    }

    public static final ConnectionManager getInstance() {
        if (mConnectionManager == null) {
            mConnectionManager = new ConnectionManager();
        }
        return mConnectionManager;
    }

    public Request getNewsLists(Context context,int page,ConnectionUtil.OnDataLoadEndListener listener){
        return ConnectionUtil.getInstance().doGet(context,URL_NEWS_LOTTERY + page,listener);
    }

    public Request getSScKaiJiang(Context context,String url,ConnectionUtil.OnDataLoadEndListener listener)
    {
        return ConnectionUtil.getInstance().doGet(context,url,listener);
    }

    public Request getSscHistory(Context context,String url,ConnectionUtil.OnDataLoadEndListener listener)
    {
        return ConnectionUtil.getInstance().doGet(context,url,listener);
    }

    public Request open(Context context, ConnectionUtil.OnDataLoadEndListener listener){

        return ConnectionUtil.getInstance().doGet(context,OPEN_URL,listener);
    }

    public Request share(Context context, ConnectionUtil.OnDataLoadEndListener listener){
        return ConnectionUtil.getInstance().doGet(context,SHARE_URL,listener);
    }


}
