package com.ab.view.spinner;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.ab.R;
import com.ab.hongyang.CommonAdapter;
import com.ab.hongyang.ViewHolder;
import com.ab.util.AbAppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolf on 2015/11/12.
 */
public class SpinnerButton extends LinearLayout {
    private TextView mNameTextView;
    private ImageView mImageView;
    private PopupWindow mPopupWindow;
    private BaseAdapter mAdapter;
    private List<String> mDataList = new ArrayList();
    private ListClickListener mListClickListener;
    private boolean isShowArrow = true;
    private Context mContext;

    public SpinnerButton(Context context) {
        super(context);
    }

    public SpinnerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SpinnerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.spinner_button, null);
        mNameTextView = (TextView) view.findViewById(R.id.name);
        mImageView = (ImageView) view.findViewById(R.id.arrow_icon);

        //通过布局注入器，注入布局给View对象
        View pop = LayoutInflater.from(context).inflate(R.layout.popwindow, null);
        DisplayMetrics displayMetrics = AbAppUtil.getDisplayMetrics(context);
        mPopupWindow = new PopupWindow(pop, displayMetrics.widthPixels * 2 / 5, displayMetrics.heightPixels * 2 / 5,true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(false);
        mPopupWindow.getContentView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();

                    WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
                    params.alpha = 1.0f;

                    ((Activity) mContext).getWindow().setAttributes(params);
                    mImageView.setImageResource(R.drawable.arrow_icon);
                    mNameTextView.setTextColor(getResources().getColor(R.color.text_black));
                }
                return false;
            }
        });
        ListView mListView = (ListView) pop.findViewById(R.id.lv_pop);
        mAdapter = new CommonAdapter(context, mDataList, R.layout.item_pop_list) {
            @Override
            public void convert(ViewHolder helper, Object item) {
                helper.setText(R.id.text, item.toString());
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                doPopShow();
                mNameTextView.setText(mDataList.get(i));
                //置为黑色
                mImageView.setImageResource(R.drawable.arrow_icon);
                mNameTextView.setTextColor(getResources().getColor(R.color.text_black));
                if (mListClickListener != null)
                    mListClickListener.click(mDataList, i);
            }
        });

        view.setOnClickListener(new NameClickListener());

        this.addView(view);
    }

    public void setTextSize(int size) {
        mNameTextView.setTextSize(size);
    }

    public void setArrowShow(boolean isShow) {
        if (isShow) {
            mImageView.setVisibility(VISIBLE);
            isShowArrow = true;
        } else {
            mImageView.setVisibility(GONE);
            isShowArrow = false;
        }
    }
    public void setListClickListener(ListClickListener listener) {
        mListClickListener = listener;
    }

    /**
     * 做popwindow的显示和隐藏
     */
    private void doPopShow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();

            WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
            params.alpha = 1.0f;

            ((Activity) mContext).getWindow().setAttributes(params);
        } else {
            //将window视图显示在myButton下面
            mPopupWindow.showAsDropDown(mNameTextView, -30, 0);
            //设置activity的背景灰度
            WindowManager.LayoutParams params = ((Activity)mContext).getWindow().getAttributes();
            params.alpha = 0.7f;

            ((Activity) mContext).getWindow().setAttributes(params);
//            mPopupWindow.showAtLocation(mNameTextView, Gravity.BOTTOM, 0, 0);
        }
    }

    public List<String> getDataList() {
        return mDataList;
    }

    /**
     * 设置列表填充的数据
     *
     * @param list
     */
    public void setDataList(List<String> list) {
        mDataList.clear();
        mDataList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void doTextAndArrow() {
        //调整箭头的方向
        if (isShowArrow) {
        if (mPopupWindow.isShowing()) {
            mImageView.setImageResource(R.drawable.arrow_icon);
            mNameTextView.setTextColor(getResources().getColor(R.color.text_black));
        } else {
            mImageView.setImageResource(R.drawable.arrow_icon_up);
            mNameTextView.setTextColor(getResources().getColor(R.color.login_btn_green));
        }
        }
    }

    /**
     * 设置显示的名字
     */
    public void setNameText(String s) {
        mNameTextView.setText(s);
    }

    public interface ListClickListener {
        void click(List list, int position);
    }

    private class NameClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            //调整箭头的方向
            doTextAndArrow();
            //显示popwindow
            doPopShow();
        }
    }


}
