package com.treatise.hospital.adapter;

import  com.treatise.hospital.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 自定义视图
 *
 */
public class TextImageAdapter extends BaseAdapter {
    private Context mContext;
    // 展示的文字
    private String[] texts;
    // 展示的图片
    private int[] images;

    public TextImageAdapter(Context context, String[] texts,
                            int[] images) {
        this.mContext = context;
        this.texts = texts;
        this.images = images;
    }

    /**
     * 元素的个数
     */
    public int getCount() {
        return texts.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // 用以生成在ListView中展示的一个个元素View
    public View getView(int position, View convertView, ViewGroup parent) {
        // 优化ListView
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item,null);
            ItemViewCache viewCache = new ItemViewCache();
            viewCache.mTextView = (TextView) convertView
                    .findViewById(R.id.itemTxt);
            viewCache.mImageView = (ImageView) convertView
                    .findViewById(R.id.itemImg);
            convertView.setTag(viewCache);
        }
        ItemViewCache cache = (ItemViewCache) convertView.getTag();
        // 设置文本和图片，然后返回这个View，用于ListView的Item的展示
        cache.mTextView.setText(texts[position]);
        cache.mImageView.setImageResource(images[position]);
        return convertView;
    }

    // 元素的缓冲类,用于优化ListView
    private class ItemViewCache {
        public TextView mTextView;
        public ImageView mImageView;
    }
}


