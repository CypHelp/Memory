package com.ab.view.bouncescrollview;

/**
 * Created by wuwf on 2015/1/16.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决ScrollView与ListView的嵌套问题
 *
 * @author zhy
 */
public class AbBounceGridView extends GridView {

    public AbBounceGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbBounceGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AbBounceGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /**
         * 解决ScrollView与ListView的嵌套问题
         */
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}

