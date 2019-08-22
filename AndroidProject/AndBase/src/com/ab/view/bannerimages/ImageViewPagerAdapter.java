package com.ab.view.bannerimages;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wuwf on 2015/6/4.
 * 轮播的viewpager的适配器
 */
public class ImageViewPagerAdapter extends PagerAdapter {
    private List<View> mImageViewList;

    public ImageViewPagerAdapter(List<View> list) {
        mImageViewList = list;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }
}
