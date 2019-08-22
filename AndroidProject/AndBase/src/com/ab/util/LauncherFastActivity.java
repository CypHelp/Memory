//package com.ab.util;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.ForegroundColorSpan;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.AnimationSet;
//import android.view.animation.RotateAnimation;
//import android.view.animation.ScaleAnimation;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.lidroid.xutils.ViewUtils;
//import com.lidroid.xutils.view.annotation.ViewInject;
//import com.lidroid.xutils.view.annotation.event.OnClick;
//import com.umeng.analytics.MobclickAgent;
//import com.wiwigo.app.common.TimerUtil;
//
//import java.util.Random;
//
//public class LauncherFastActivity extends Activity {
//    RelativeLayout bgLeft;
//    RelativeLayout bgRight;
//    int i = 60;
//    ViewGroup.LayoutParams lpLeft;
//    ViewGroup.LayoutParams lpLeftHeight;
//    ViewGroup.LayoutParams lpRightHeight;
//    ViewGroup.LayoutParams lpRight;
//    /**
//     * 在右侧的桌面控件显示加速百分百的textview
//     */
//    @ViewInject(R.id.launcher_left)
//    private TextView mLauncherLeft;
//    /**
//     * 在左侧的桌面控件显示加速百分百的textview
//     */
//    @ViewInject(R.id.launcher_right)
//    private TextView mLauncherRight;
//    /**
//     * 在右侧的桌面控件图片
//     */
//    @ViewInject(R.id.anim_image_left)
//    private ImageView mImageViewLeft;
//    /**
//     * 在左侧的桌面控件图片
//     */
//    @ViewInject(R.id.anim_image_right)
//    private ImageView mImageViewRight;
//    /**
//     * 风扇图片高度
//     */
//    private int mImageHeight;
//    /**
//     * 屏幕宽度的一半
//     */
//    private float mWidth;
//    /**
//     * 距离屏幕左侧或右侧距离
//     */
//    private int mPadding;
//    /**
//     * 本次提速数值百分比
//     */
//    private int fastNum;
//    private Handler mHandler = new Handler() {
//
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 10:
//                    new TimerUtil().timerDo(true, 200, 0, 1, new TimerUtil.TimerListener() {
//                        @Override
//                        public void timerBegin() {
//                        }
//
//                        @Override
//                        public void timerRun() {
//                            i += mWidth / 200;
//                            lpRight.width = i;
//                            lpLeft.width = i;
//                            mHandler.sendEmptyMessage(11);
//                        }
//
//                        @Override
//                        public void timerOver() {
//                            mHandler.sendEmptyMessage(102);
//                        }
//                    });
//                    break;
//                case 11:
//                    lpRight.height = mImageHeight;
//                    lpLeft.height = mImageHeight;
//                    mLauncherLeft.setPadding(20, 0, mPadding - 20, 0);
//                    mLauncherRight.setPadding(mPadding - 20, 0, 20, 0);
//                    mLauncherRight.setLayoutParams(lpRight);
//                    mLauncherLeft.setLayoutParams(lpLeft);
//                    break;
//                case 1:
//                    finish(); // 动画播放结束，结束我们的Activity界面
//                    break;
//                case 102:
//                    if (NewMainActivity.iscon) {
//                        String text = "本次提速" + fastNum + "%";
//                        SpannableString spannableString = new SpannableString(text);
//                        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 4, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        mLauncherLeft.setText(spannableString);
//                        mLauncherRight.setText(spannableString);
//                        mHandler.sendEmptyMessageDelayed(1, 1000);
//                    } else {
//                        mLauncherLeft.setText("网络未连接");
//                        mLauncherRight.setText("网络未连接");
//                        mHandler.sendEmptyMessageDelayed(1, 3000);
//                    }
//            }
//        }
//    };
//
//    private void play(int time) {
//        ViewGroup.LayoutParams params = mImageViewLeft.getLayoutParams();
//        //放大动画
//        Animation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, 50, 50);
//        scaleAnimation.setDuration(200);
//        scaleAnimation.setFillAfter(true);
//        scaleAnimation.setStartOffset(0);
//        //旋转动画
//        Animation rotateAnimation = new RotateAnimation(0, 1.76f * time, params.width / 2.0f, params.width / 2.0f);
//        rotateAnimation.setDuration(time - 200);
//        rotateAnimation.setStartOffset(0);
//        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.addAnimation(rotateAnimation);
//        mImageViewLeft.startAnimation(animationSet);
//        mImageViewRight.startAnimation(animationSet);
//    }
//
//    @OnClick(R.id.main)
//    private void dismiss(View view) {
//        this.finish();
//    }
//
//    @Override
//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        MobclickAgent.onEvent(this, "luncher_fast");
//
//        Intent intent = getIntent();
//        setContentView(R.layout.activity_launcher_fast);
//        ViewUtils.inject(this);
//        long systemTime = System.currentTimeMillis();
//        SharedPreferences sharedPreferences = getSharedPreferences("shortCutTime", MODE_PRIVATE);
//        long fileTime = sharedPreferences.getLong("time", 0l);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        //0-10s 0-5%
//        //11-30s 5%-10%
//        //>30s  10%-15%
//        if (systemTime - fileTime < 10000) {
//            fastNum = randomNum(0, 5);
//            if (randomNum(1, 10) == 1) {
//                fastNum = randomNum(5, 10);
//            }
//        } else if (systemTime - fileTime < 30000) {
//            fastNum = randomNum(5, 10);
//            if (randomNum(1, 10) == 1) {
//                fastNum = randomNum(10, 15);
//            }
//        } else {
//            fastNum = randomNum(10, 15);
//            if (randomNum(1, 10) == 1) {
//                fastNum = randomNum(15, 20);
//            }
//        }
//        editor.putLong("time", systemTime);
//        editor.commit();
//        bgLeft = (RelativeLayout) findViewById(R.id.anim_layout_left);
//        bgRight = (RelativeLayout) findViewById(R.id.anim_layout_right);
//        lpRight = mLauncherRight.getLayoutParams();
//        lpLeft = mLauncherLeft.getLayoutParams();
//        lpLeftHeight = mLauncherLeft.getLayoutParams();
//        lpRightHeight = mLauncherLeft.getLayoutParams();
//        lpLeft = mLauncherLeft.getLayoutParams();
//        Rect rect = intent.getSourceBounds();
//        if (rect != null) {
//            int i = 20;
//            //圆心位置
//            int k = i + rect.top;
//            DisplayMetrics dm = getResources().getDisplayMetrics();
//            int w_screen = dm.widthPixels;
//
//            //弹出textview显示长度
//            mWidth = w_screen / 2f;
//            android.widget.RelativeLayout.LayoutParams layoutparamsLeft = (android.widget.RelativeLayout.LayoutParams) bgLeft
//                    .getLayoutParams();
//            layoutparamsLeft.rightMargin = w_screen - rect.right + 20;
//            layoutparamsLeft.topMargin = k - layoutparamsLeft.height / 2;
//            android.widget.RelativeLayout.LayoutParams layoutparamsRight = (android.widget.RelativeLayout.LayoutParams) bgRight
//                    .getLayoutParams();
//            layoutparamsRight.leftMargin = rect.left + 20;
//            layoutparamsRight.topMargin = k - layoutparamsRight.height / 2;
//            if ((rect.right - rect.left) / 2 + rect.left >= w_screen / 2) {
//                //往左走
//                bgLeft.setVisibility(View.VISIBLE);
//                bgRight.setVisibility(View.GONE);
//                ViewGroup.LayoutParams params = mImageViewLeft.getLayoutParams();
//                mImageHeight = params.height;
//                mPadding = params.width;
//            } else {
//                //往右走
//                bgRight.setVisibility(View.VISIBLE);
//                bgLeft.setVisibility(View.GONE);
//                ViewGroup.LayoutParams params = mImageViewRight.getLayoutParams();
//                mImageHeight = params.height;
//                mPadding = params.width;
//            }
//            bgLeft.setLayoutParams(layoutparamsLeft);
//            bgRight.setLayoutParams(layoutparamsRight);
//        }
//        int animationTime = randomNum(2000, 4500);
//        play(animationTime);
//        mHandler.sendEmptyMessageDelayed(10, animationTime);
//    }
//
//    /**
//     * 生成指定范围随机数
//     *
//     * @param min 最小值
//     * @param max 最大值
//     * @return 随机数
//     */
//    private int randomNum(int min, int max) {
//        Random random = new Random();
//        return random.nextInt(max) % (max - min + 1) + min;
//    }
//}
