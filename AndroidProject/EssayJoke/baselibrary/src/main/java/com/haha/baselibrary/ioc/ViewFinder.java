package com.haha.baselibrary.ioc;

import android.app.Activity;
import android.view.View;


/**
 * Author: Yp_Love
 * Date 2019-04-14
 * Description: View 的findViewById的辅助类
 */
public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
    }


    public ViewFinder(View view) {

    }

    View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
