package com.example.ershou.Util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import android.util.Log;
/**
 * 文件名称：UploadImage.java
 *
 * 版权信息：Apache License, Version 2.0
 *
 * 功能描述：实现图片文件上传�?
 *
 * 创建日期�?011-5-10
 *
 * 作�?：Bert Lee
 */
/*
 * 修改历史�?
 */
/**
 * 文件名称：UploadImage.java
 *
 * 版权信息：Apache License, Version 2.0
 *
 * 功能描述：实现图片文件上传�?
 *
 * 创建日期�?011-5-10
 *
 * 作�?：Bert Lee
 */

/*
 * 修改历史�?
 */
public class UploadImage {
    private static final String TAG = "uploadFile";


    private static final int TIME_OUT = 10 * 1000; // 超时时间


    private static final String CHARSET = "utf-8"; // 设置编码


    /**
     * Android上传文件到服务端
     * 
     * @param file �?��上传的文�?
     * @param RequestURL 请求的rul
     * @return 返回响应的内�?
     */
    public static String uploadFile(File file, String RequestURL,String id,String name) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型


        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入�?
            conn.setDoOutput(true); // 允许输出�?
            conn.setUseCaches(false); // 不允许使用缓�?
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意�?name里面的�?为服务端�?��key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后�?���?比如:abc.png
                 */


                sb.append("Content-Disposition: form-data; name=\""+name+"\"; filename=\""
                        + file.getName() +"\"; EID=\""+id+"\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应�?200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                // if(res==200)
                // {
                Log.e(TAG, "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                Log.e(TAG, "result : " + result);
                // }
                // else{
                // Log.e(TAG, "request error");
                // }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Android上传文件到服务端
     * 
     * @param file �?��上传的文�?
     * @param RequestURL 请求的rul
     * @return 返回响应的内�?
     */
    public static String uploadFiles(File file, String RequestURL,Map<String, String> param) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String params = "";

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入�?
            conn.setDoOutput(true); // 允许输出�?
            conn.setUseCaches(false); // 不允许使用缓�?
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = null;
            if (param != null && param.size() > 0) {
            	Iterator<String> it = param.keySet().iterator();
            	while (it.hasNext()) {
            	sb = null;
            	sb = new StringBuffer();
            	String key = it.next();
            	String value = param.get(key);
            	sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
            	sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
            	sb.append(value).append(LINE_END);
            	params = sb.toString();
            	dos.write(params.getBytes());
            	// dos.flush();
            	}
            	}
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                /**
                 * 这里重点注意�?name里面的�?为服务端�?��key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后�?���?比如:abc.png
                 */
            	sb = null;
            	sb = new StringBuffer();
            	sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                sb.append("Content-Disposition: form-data; name=\"tupian\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应�?200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                // if(res==200)
                // {
                Log.e(TAG, "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                Log.e(TAG, "result : " + result);
                // }
                // else{
                // Log.e(TAG, "request error");
                // }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}