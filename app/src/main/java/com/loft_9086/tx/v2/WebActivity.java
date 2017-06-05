package com.loft_9086.tx.v2;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.loft_9086.tx.v2.base.BaseActivity;
import com.loft_9086.tx.v2.utils.ThemeUtil;
import com.loft_9086.tx.v2.view.TitleView;
public class WebActivity extends BaseActivity {
	WebView mWebView;
	TextView mLoadFailed;
	View mLoading;
	TitleView mTitleView;
	public static final String URL_TITLE = "ext_title";
	public static final String URL_TITLE_URL = "ext_url";
	public static void  onNewInstance(Activity act,String title,String url){
		Intent intent = new Intent();
		intent.setClass(act,WebActivity.class);
		intent.putExtra(URL_TITLE,title);
		intent.putExtra(URL_TITLE_URL,url);
		act.startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(ThemeUtil.getRemoteId(this, "native_fragment_webview", ThemeUtil.RESOURCES_TYPE_LAYOUT, 0));
		mTitleView = (TitleView)findViewById(R.id.title_view);
		initSystemBarTint(mTitleView, Color.TRANSPARENT);
		mTitleView.setTitleBackVisibility(View.VISIBLE);
		mTitleView.setTitleBackOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();;
			}
		});
		mWebView = (WebView)findViewById(ThemeUtil.getRemoteId(this, "activty_wb_content", ThemeUtil.RESOURCES_TYPE_ID, 0));
		mLoadFailed = (TextView)findViewById(ThemeUtil.getRemoteId(this, "load_faild", ThemeUtil.RESOURCES_TYPE_ID, 0));
		mLoading = findViewById(ThemeUtil.getRemoteId(this, "loading", ThemeUtil.RESOURCES_TYPE_ID, 0));

		mLoadFailed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mWebView.reload();
			}
		});
		
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		String url = this.getIntent().getStringExtra(URL_TITLE_URL);
		String title = this.getIntent().getStringExtra(URL_TITLE);
		mTitleView.setTitle(title);
		mWebView.loadUrl(url);
		
	}

	private class MyWebViewClient extends WebViewClient{
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(url == null){
				return false;
			}
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			mLoading.setVisibility(View.GONE);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			mLoading.setVisibility(View.VISIBLE);
			mLoadFailed.setVisibility(View.GONE);
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
			mLoadFailed.setVisibility(View.VISIBLE);
		}
	}
	@Override
	protected void onPause ()
	{
		mWebView.reload ();

		super.onPause ();
	}
}
