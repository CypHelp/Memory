package com.ab.activity.chooseimage;

import com.ab.activity.bean.ImageItem;

import java.util.List;

/**
 * 相册对象
 */
public class ImageBucket {
    public int count = 0;
    public String bucketName;
    public List<ImageItem> imageList;
    public boolean selected = false;
}
