
package com.andbase.library.view.sample;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Copyright upu173.com
 * Author 还如一梦中
 * Date 2016/6/14 17:54
 * Email 396196516@qq.com
 * Info 可设置是否滑动的ViewPager.
 */

public class AbViewPager extends ViewPager {

	private boolean enabled;

	public AbViewPager(Context context) {
		super(context);
		this.enabled = true;
	}
	

	public AbViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onTouchEvent(event);
		}

		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onInterceptTouchEvent(event);
		}

		return false;
	}

	/**
	 * 是否允许滑动
	 * @param enabled
     */
	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
