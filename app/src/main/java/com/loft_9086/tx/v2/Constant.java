package com.loft_9086.tx.v2;

import java.io.File;

import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.MD5FileNameGenerator;
import com.nostra13.universalimageloader.core.MD5;

/**
 * 
 * @version 1.0
 */
public class Constant {






	/** 开发模式 */
	public static final boolean DEVELOPER_MODE = false;
	// 注意，这里的端口不是tomcat的端口，CIM端口在服务端spring-cim.xml中配置的，没改动就使用默认的28888
	public static int CIM_SERVER_PORT = 28888;
	public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getPath() + "/com.wbaiju.ichat/";
	/** 缓存文件夹地址 */
	public static final String CACHE_DIR = ROOT_DIR + "cache";
	public static final String LOG_DIR = ROOT_DIR + "log";
	public static final String VIDEO_DIR = ROOT_DIR + "video";
	public static final String EXPORT_DIR = Environment.getExternalStorageDirectory().getPath() + "/";//"/Wbaiju/";
	public static final String EXPORT_IMAGE_DIR = EXPORT_DIR ;//+ "image/";
	public static final String EXPORT_VIDEO_DIR = EXPORT_DIR + "video/";
	/** 存放下载文件夹地址 */
	public static final String DOWNLOAD_DIR = ROOT_DIR + "download2";

	public static final String MIANZE = "http://www.godgiftgame.com/well/page/wshow?id=cbb75fa6e71225542bff892fb7d0e152";
	public static final String JIANJIE = "http://www.godgiftgame.com/well/page/wshow?id=8a08478dea376c3839f0a4fce3be1e42";

}
