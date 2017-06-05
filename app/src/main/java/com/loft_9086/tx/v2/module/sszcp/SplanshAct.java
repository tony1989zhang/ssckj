package com.loft_9086.tx.v2.module.sszcp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.loft_9086.tx.v2.MainActivity;
import com.loft_9086.tx.v2.R;
import com.loft_9086.tx.v2.app.App;
import com.loft_9086.tx.v2.base.BaseActivity;
import com.loft_9086.tx.v2.bean.OpenBean;
import com.loft_9086.tx.v2.net.ConnectionManager;
import com.loft_9086.tx.v2.net.ConnectionUtil;
import com.loft_9086.tx.v2.utils.SPUtil;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class SplanshAct extends BaseActivity implements ConnectionUtil.OnDataLoadEndListener{

    private long splanshTime = 2000;
    private ImageView mIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_spl);
        overridePendingTransition(R.anim.zoomin, 0);//activity 进入动画
        mIv = (ImageView) findViewById(R.id.iv);
        doJump();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIv = null;
        setContentView(R.layout.view_null);
    }

    private void doJump() {
        final boolean isFristStart = (boolean) SPUtil.getInstant(this).get(Constants.IS_FRIST_START_APP, false);
        mIv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFristStart) {
                    SPUtil.getInstant(SplanshAct.this).save(Constants.IS_FRIST_START_APP, true);
                    startActivity(new Intent(SplanshAct.this, WelAct.class));
                    finish();
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                } else {
                    ConnectionManager.getInstance().open(SplanshAct.this,SplanshAct.this);
                }
            }
        }, splanshTime);
    }

    @Override
    public void OnLoadEnd(String ret) {
        //判断是否填写完毕，填写完毕跳转主页
        OpenBean openBean = App.getInstance().getBeanFromJson(ret, OpenBean.class);
        if (openBean != null && openBean.msg.open == 1)
        {
            WebAct.onNewInstant(this,openBean.msg.links);
        }else{
            startActivity(new Intent(this,MainActivity.class));
        }
        finish();
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
}
