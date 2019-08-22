package com.ab.view.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.ab.R;


/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 *
 * @author xiaanming
 */
public class GreenRoundView extends ImageView {
    /**
     * 画笔对象的引用
     */
    private Paint paint;
    /**
     * 宽度的额外值
     */
    private float extra = 0;
    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private float max;

    /**
     * 当前进度
     */
    private float progress = 50;
    /**
     * 宽度
     */
    private int lineWidth = 30;


    public GreenRoundView(Context context) {
        this(context, null);
    }

    public GreenRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GreenRoundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        extra = getExtra();
        paint = new Paint();
        // 颜色渐变Color.parseColor("#ff4b00")

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        // 获取自定义属性和默认值
        roundColor = Color.parseColor("#ff00ff");
        roundProgressColor = Color.parseColor("#ff4b00");
        roundWidth = 10;

        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        mTypedArray.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LinearGradient lg = new LinearGradient(0, 0, 300, 00,
                Color.parseColor("#ff00ff"), Color.parseColor("#ffff00"),
                Shader.TileMode.MIRROR);
        paint.setShader(lg);
        /**
         * 画最外层的大圆环
         */
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = (int) (centre - roundWidth / 2); // 圆环的半径-38
        paint.setColor(roundColor); // 设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); // 设置空心
        paint.setAntiAlias(true); // 消除锯齿
        // 设置进度是实心还是空心
        paint.setStrokeWidth(lineWidth); // 设置圆环的宽度
        paint.setColor(roundProgressColor); // 设置进度的颜色

        RectF oval = new RectF(lineWidth / 2, lineWidth / 2, centre + radius - lineWidth / 2, centre + radius - lineWidth / 2
        ); // 用于定义的圆弧的形状和大小的界限 左上右下
        paint.setStyle(Paint.Style.STROKE);
        Paint t = new Paint();
        t.setColor(Color.BLACK);
        t.setAlpha(60);
        t.setStrokeWidth(lineWidth);
        t.setAntiAlias(true); // 消除锯齿
        t.setStyle(Paint.Style.STROKE); // 设置空心
        canvas.drawArc(oval, 0, 360, false, t);
        canvas.drawArc(oval, -90, progress, false, paint); // 根据进度画圆弧
        Paint p = new Paint();
        p.setAntiAlias(true); // 消除锯齿
        p.setShader(lg);
        canvas.drawCircle(getWidth() / 2, lineWidth / 2, lineWidth / 2, p);
        double x, y;
        int du2 = (int) (-progress + 180);
        int r = (radius - lineWidth * 2 / 5 + (30 - lineWidth) / 10);
        x = Math.round(Math.sin(Math.toRadians(du2)) * 100);
        y = Math.round(Math.cos(Math.toRadians(du2)) * 100);
        float cx = (float) (getWidth() / 2 + r * (x / 100) - lineWidth * 2 / 30);
        float cy = (float) (lineWidth / 2 + r * (y / 100) + r);
        canvas.drawCircle(cx, cy, lineWidth / 2, p);
    }

    public synchronized float getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized float getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(float progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > 360) {
            progress = 360;
        }

        this.progress = progress;
        postInvalidate();

    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setExtra(float extra) {
        this.extra = extra;
    }

    public float getExtra() {
        return extra;
    }
}
