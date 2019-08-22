package com.ab.http.wolf;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

import java.util.HashMap;

/**
 * Created by wolf on 2016/3/2.
 */
public class HttpTool {
    /**
     * 发送网络请求的方法
     */
    public static void sendRequest(int requestCode, HashMap<String, String> map,
                                   final GetDataCallBack call) {
        String url = getUrl(requestCode);
        send(isGet(requestCode), url, map, call);
    }

    /**
     * 真正的请求
     */
    private static void send(boolean get, String url, HashMap<String, String> map,
                             final GetDataCallBack call) {
        ResponseListener listener = new ResponseListener(call);
        // 如果是get请求
        if (get) {
            OkHttpUtils.get().url(url).params(map).build().execute(listener);
            return;
        }

        //post请求
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.size() == 0) {
            map.put("", "");
        }
        OkHttpUtils.post().url(url).params(map).build().execute(listener);
    }

    /**
     * 根据请求码获取url地址
     */
    private static String getUrl(int requestCode) {
        String url = null;
        switch (requestCode) {
            case 100:
                url = "http://basead.747.cn/" + "ad/ad/" + "ahowie050001.json" + "?tag=find";
                break;
            default:
                break;
        }

        return url;
    }

    /**
     * 如果请求码是1XX则是get请求，如果是2XX则是post请求
     */
    private static boolean isGet(int requestCode) {
        if (requestCode >= 0 && requestCode < 100) {
            return true;
        }
        return false;
    }

    private static class ResponseListener extends StringCallback {
        private GetDataCallBack call;

        public ResponseListener(GetDataCallBack call) {
            this.call = call;
        }


        @Override
        public void onError(Call c, Exception e) {
            if (call != null)
                call.failure();
        }

        @Override
        public void onResponse(String s) {
            if (call != null)
                call.success(s);
        }
    }


}
