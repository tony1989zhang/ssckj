package com.loft_9086.tx.v2.app;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.google.gson.Gson;
import com.loft_9086.tx.v2.Constant;
import com.loft_9086.tx.v2.utils.ImageFileCacheUtils;
import com.loft_9086.tx.v2.view.ToastTool;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.MD5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.json.JSONException;
import org.json.JSONObject;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.util.Utils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/1/1.
 */
public class App extends Application{

    public static final String DATA_DIR = Environment.getExternalStorageDirectory() + File.separator + ImageFileCacheUtils.CHCHEDIR + File.separator;

    private static App mApp;
    public List<WeakReference<Activity>> aliveActivitys = new ArrayList<WeakReference<Activity>>();

    /**屏幕高度*/
    public int mScreenHeight;
    /**屏幕宽度*/
    public int mScreenWidth;
    /**状态栏高度*/
    public int mTintInsertTop;
    /**状态栏+ActionBar高度*/
    public int mTintInsertTopWithActionBar;

    public boolean mIsIntelligent = false;
    public boolean mIsHeadSetPluged = false;
    public static App getInstance(){
        if(mApp == null){
            mApp = new App();
        }

        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Bmob.initialize(this,"ec8a600d550e63dfbcc668a14d1886cb","Bmob");
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        initImageLoader();
        checkAppUpdate();
    }

    private void checkAppUpdate() {
        ToastTool.init(this);
        UpdateConfig.getConfig()
//        https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/update.json
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                .url("http://oqii1pdkq.bkt.clouddn.com/update2.json")
//                .checkEntity(new CheckEntity().setMethod(HttpMethod.GET).setUrl("http://www.baidu.com"))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) {
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        try {
                            JSONObject object = new JSONObject(response);
                            Update update = new Update(response);
                            // 此apk包的更新时间
                            update.setUpdateTime(System.currentTimeMillis());
                            // 此apk包的下载地址
                            update.setUpdateUrl(object.optString("update_url"));
                            // 此apk包的版本号
                            update.setVersionCode(object.optInt("update_ver_code"));
                            // 此apk包的版本名称
                            update.setVersionName(object.optString("update_ver_name"));
                            // 此apk包的更新内容
                            update.setUpdateContent(object.optString("update_content"));
                            // 此apk包是否为强制更新
                            update.setForced(false);
                            // 是否显示忽略此次版本更新按钮
                            update.setIgnore(object.optBoolean("ignore_able",false));
                            return update;
                        } catch (JSONException e) {
                            return null;
                        }

                    }
                })
                // TODO: 2016/5/11 除了以上两个参数为必填。以下的参数均为非必填项。
                .checkCB(new UpdateCheckCB() {

                    @Override
                    public void onCheckError(Throwable t) {
                        ToastTool.show("更新失败：code:" + t.getMessage());
//                        Toast.makeText(MyApplication.this, "更新失败：code:" + code + ",errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserCancel() {
                        ToastTool.show("用户取消更新");
                    }

                    @Override
                    public void onCheckIgnore(Update update) {
                        ToastTool.show("用户忽略此版本更新");
                    }

                    @Override
                    public void onCheckStart() {
                        // 此方法的回调所处线程异于其他回调。其他回调所处线程为UI线程。
                        // 此方法所处线程为你启动更新任务是所在线程
                        Utils.getMainHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastTool.show("启动更新任务");
                            }
                        });
                    }

                    @Override
                    public void hasUpdate(Update update) {
                        ToastTool.show("检查到有更新");
                    }

                    @Override
                    public void noUpdate() {
                        ToastTool.show("无更新");
                    }
                })
                // apk下载的回调
                .downloadCB(new UpdateDownloadCB(){
                    @Override
                    public void onUpdateStart() {
                        ToastTool.show("下载开始");
                    }

                    @Override
                    public void onUpdateComplete(File file) {
                        ToastTool.show("下载完成");
                    }

                    @Override
                    public void onUpdateProgress(long current, long total) {
                        System.out.println("current = [" + current + "], total = [" + total + "]");
                    }

                    @Override
                    public void onUpdateError(Throwable t) {
                        ToastTool.show("下载失败:" + t.getMessage());
                    }
                });
    }

    public <T>T getBeanFromJson(String ret,Class<T> c){
        T bean = null;
        try{
            bean = new Gson().fromJson(ret,c);
        } catch (Exception e) {
            //format json error
        }
        if(bean == null){
            try {
                bean = c.newInstance();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bean;
    }






    /**
     * 初始化 ImageLoader
     */
    public void initImageLoader() {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).memoryCacheExtraOptions(480, 800)
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory().memoryCache(new WeakMemoryCache()).memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCache(new UnlimitedDiscCache(new File(Constant.CACHE_DIR), new MD5FileNameGenerator())).denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .imageDownloader(new BaseImageDownloader(mApp)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                        // .memoryCache(new WeakMemoryCache())
                .threadPoolSize(3).build();
        ImageLoader.getInstance().init(config);
    }

    public void finishAllActivity(){
        for (int i = 0;i < aliveActivitys.size();i++)
        {
            if (aliveActivitys.get(i) != null)
            {
                aliveActivitys.get(i).get().finish();
            }
        }
    }
    public Activity getTopActivity(){
        return aliveActivitys.get(aliveActivitys.size() - 1).get();
    }
}
