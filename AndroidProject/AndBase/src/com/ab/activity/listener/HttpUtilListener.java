package com.ab.activity.listener;

import com.ab.http.wolf.GetDataCallBack;

import java.util.HashMap;

/**
 * Created by wolf on 2015/11/18.
 */
public interface HttpUtilListener {
    void sendRequest(int requestCode, HashMap<String, String> map,
                     final GetDataCallBack call);
}
