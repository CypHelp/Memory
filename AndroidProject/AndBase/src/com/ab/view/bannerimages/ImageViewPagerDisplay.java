package com.ab.view.bannerimages;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.ab.R;
import com.ab.util.AbViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuwf on 2015/6/4.
 * 展示轮播图片及小点
 * <p/>用法，在布局里include bannerlayout，然后new  ImageViewPagerDisplay传过来当前的布局view和数据集合
 */
public class ImageViewPagerDisplay {
    /**
     * ViewPager
     */
    private ViewPager mViewPager;
    /**
     * 轮播图片集合
     */
    private List<View> mImageViewList;

    /**
     * 图片ID集合
     */
    private List<AdBean> banners;
    /**
     * 当前图片索引
     */
    private int currentItem = 0;
    /**
     * 我也不知道是啥....
     */
    private ScheduledExecutorService mScheduledExecutorService;
    /**
     * 小点
     */
    private List<View> dots = new ArrayList<>();
    private Context mContext;
    /**
     * 放小点的布局
     */
    private LinearLayout mDotLinear;

    private View mView;

    private BannerClickListener mBannerClickListener;
    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };

    /**
     * 初始化图片加载
     */
    public ImageViewPagerDisplay(Context context, View view, List<AdBean> banners) {
        this.banners = banners;
        mContext = context;
        init(view);
    }

    private void init(View view) {
        mView = view;
        mViewPager = (ViewPager) mView.findViewById(R.id.banner_viewpager);
        mDotLinear = (LinearLayout) mView.findViewById(R.id.dot_linear);
        //开始轮播
        initBanner();
    }

    public void setBannerClickListener(BannerClickListener listener) {
        mBannerClickListener = listener;
    }

    /**
     * 初始化
     */
    private void initBanner() {
        mImageViewList = new ArrayList<>();
        // 初始化图片资源
        for (int i = 0; i < banners.size(); i++) {
            final int position = i;
            AdBean banner1 = banners.get(i);
            ImageView imageView = new ImageView(mContext);
            ImageDisplay.display(mContext, imageView, banner1.getImage(), R.drawable.loading);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBannerClickListener.click(position, banners);
                }
            });
            mImageViewList.add(imageView);
        }
        mDotLinear.removeAllViews();
        for (int i = 0; i < banners.size(); i++) {
            View dot = new View(mContext);
            dot.setBackgroundResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) AbViewUtil.dip2px(mContext, 5), (int) AbViewUtil.dip2px(mContext, 5));
            params.setMargins((int) AbViewUtil.dip2px(mContext, 1.5f), 0, (int) AbViewUtil.dip2px(mContext, 1.5f), 0);
            dot.setLayoutParams(params);
            mDotLinear.addView(dot);
            dots.add(dot);
        }
        if (dots.size() > 0) {
            //让第一个小点变红
            dots.get(0).setBackgroundResource(R.drawable.dot_focused);
        }

        mViewPager.setAdapter(new ImageViewPagerAdapter(mImageViewList));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());

        currentItem = 0;

        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 2, 4, TimeUnit.SECONDS);
    }

    public interface BannerClickListener {
        void click(int position, List<AdBean> banners);
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 执行切换任务
     */
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (mViewPager) {
                currentItem = (currentItem + 1) % mImageViewList.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

}
