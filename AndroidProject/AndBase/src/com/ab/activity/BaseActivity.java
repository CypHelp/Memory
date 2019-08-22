package com.ab.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import com.ab.activity.listener.HttpUtilListener;
import com.ab.http.wolf.GetDataCallBack;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wolf on 2015/10/29.
 * 适用于使用AbPullToRefreshView的界面的刷新和加载更多功能，可继承该类，继承后调用initRefreshView方法
 * <p/>实现getHttpUtilListener方法，传入自己的httpUtil对象
 */
public abstract class BaseActivity extends AbActivity {
    public static final int STATE_REFRESH = 0;// 下拉刷新
    public static final int STATE_MORE = 1;// 加载更多
    protected Context mContext;
    protected List mDataList = new ArrayList<>();
    protected int onePageSize;
    private AbPullToRefreshView mAbPullToRefreshView;
    private BaseAdapter mAdapter;
    private int actionType;
    /**
     * 当前是第几页
     */
    private int pageNum = 1;

    protected void setOnePageSize(int size) {
        onePageSize = size;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected abstract HttpUtilListener getHttpUtilListener();

    /**
     * 初始化下拉刷新进度条
     */
    protected void initRefreshView(int requestcode, HashMap<String, String> map, GetDataCallBack listener, AbPullToRefreshView abPullToRefreshView, AdapterView view, BaseAdapter adapter) {
        mAbPullToRefreshView = abPullToRefreshView;
        mAdapter = adapter;
//        ListView listView = (ListView) mAbPullToRefreshView.findViewById(R.id.mListView);
        view.setAdapter(mAdapter);

        mAbPullToRefreshView
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        actionType = STATE_REFRESH;
                        refreshData(requestcode, map, listener, 1);
                    }
                });
        mAbPullToRefreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                actionType = STATE_MORE;
                refreshData(requestcode, map, listener, pageNum);
            }
        });
//        // 设置进度条的样式
//        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable
//                .default_ptr_rotate));
//        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable
//                .default_ptr_rotate));
        actionType = STATE_REFRESH;
        //初次取数据
        refreshData(requestcode, map, listener, 1);
    }

    /**
     * 第一次获取数据和下拉刷新
     */
    protected void refreshData(int requestcode, HashMap<String, String> map, GetDataCallBack listener, final int
            page) {
        map.put("pageNum", page + "");
        //查询数据
        getHttpUtilListener().sendRequest(requestcode, map, listener);
    }

    protected void doSuccess(List list) {
//        closeProgressBar();
        if (list.size() > 0) {
            if (actionType == STATE_REFRESH) {
                // 当是下拉刷新操作时，将当前页的编号重置为1，并把mDataList清空，重新添加
                pageNum = 1;
                mDataList.clear();
            }
            //如果是加载更多
            // 这里在每次加载完数据后，将当前页码+1，
            mDataList.addAll(list);
            pageNum++;
            mAdapter.notifyDataSetChanged();

        } else if (actionType == STATE_MORE) {
            AbToastUtil.showToast(mContext, "没有更多数据了");
        } else if (actionType == STATE_REFRESH) {
            AbToastUtil.showToast(mContext, "没有数据");
        }
        mAbPullToRefreshView.onHeaderRefreshFinish();
        mAbPullToRefreshView.onFooterLoadFinish();
    }

    protected void doFailure(int code) {
//        closeProgressBar();
        if (code == 9016 || code == 9010 || code == 9015)
            AbToastUtil.showToast(mContext, "网络连接失败");
        mAbPullToRefreshView.onHeaderRefreshFinish();
        mAbPullToRefreshView.onFooterLoadFinish();
    }

}