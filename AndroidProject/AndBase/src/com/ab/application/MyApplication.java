package com.ab.application;

import android.app.Application;

/**
 * @author wuwf
 * 
 * @date 2014-6-6
 * 
 **/

public class MyApplication extends Application {

	private static MyApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	public static MyApplication getInstance() {
        return instance;
    }

}
