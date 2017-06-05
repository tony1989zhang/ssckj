package com.loft_9086.tx.v2.module.sscls;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.loft_9086.tx.v2.base.ActivityForFragmentNormal;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public class SscHositoryAct extends ActivityForFragmentNormal {
    private static final String SSC_HOSITORY_TITLE = "ssc_hostitory_title";
    private  static final String SSC_HOSIROTRY_URL = "ssc_hository_url";
    private  static final String SSC_HOSIROTRY_TYPE = "ssc_hository_type";
    public static void onNewInstant(Activity act, String title, String url,int type){
        Intent intent = new Intent(act, SscHositoryAct.class);
        intent.putExtra(SSC_HOSIROTRY_URL,url);
        intent.putExtra(SSC_HOSITORY_TITLE,title);
        intent.putExtra(SSC_HOSIROTRY_TYPE,type);
        act.startActivity(intent);
    }


    @Override
    public Fragment initFragment() {
        String  sscTitle = getIntent().getStringExtra(SSC_HOSITORY_TITLE);
        String   sscUrl = getIntent().getStringExtra(SSC_HOSIROTRY_URL);
        int type = getIntent().getIntExtra(SSC_HOSIROTRY_TYPE,1);
        return HistorySsFragment.newInstance(sscTitle,sscUrl,type);
    }
}
