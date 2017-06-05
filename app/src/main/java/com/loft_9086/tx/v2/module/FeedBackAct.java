package com.loft_9086.tx.v2.module;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loft_9086.tx.v2.R;
import com.loft_9086.tx.v2.base.BaseActivity;
import com.loft_9086.tx.v2.view.TitleView;

public class FeedBackAct extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feedback);
        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        titleView.setTitle("用户反馈");
        titleView.setTitleBackVisibility(View.VISIBLE);
        titleView.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.tv_submit_ok);
        textView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_view)
        {
        }else {
            Toast.makeText(this, "谢谢您的反馈，我们正在处理", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
