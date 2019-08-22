package com.ab.view.bannerimages;

import android.content.Context;
import android.widget.ImageView;
import com.ab.R;
import com.ab.hongyang.imageloader.ImageLoader;
import com.bumptech.glide.Glide;

/**
 * Created by wuwf on 2015/6/12.
 * 图片展示
 */
public class ImageDisplay {
    public static void display(Context context, ImageView imageView, String url, int defauldImage) {
        if (defauldImage == 0) {
            defauldImage = R.drawable.ic_launcher;
        }
        Glide.with(context).load(url).placeholder(defauldImage).centerCrop().crossFade().into(imageView);
    }

    public static void display(String url, ImageView imageView) {
        if (url == null) {
            return;
        }
        ImageLoader.getInstance().loadImage(url, imageView, true);
    }

    public static void displayFromLocal(String url, ImageView imageView) {
        if (url == null) {
            return;
        }
        ImageLoader.getInstance().loadImage(url, imageView, false);
    }
}
