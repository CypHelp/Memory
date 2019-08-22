package com.ab.activity.chooseimage;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ab.R;
import com.ab.activity.bean.ImageItem;
import com.ab.constant.Constant;
import com.ab.constant.IntentConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 图片选择
 */
@SuppressWarnings("ALL")
public class ImageChooseActivity extends Activity {
    public static ImageChooseActivity mImageChooseActivity;
    /**
     * 标题
     */
    private TextView mTitleTextView;
    /**
     * 完成按钮
     */
    private Button mFinishBtn;
    private List<ImageItem> mDataList = new ArrayList<>();
    private String mBucketName;
    private int availableSize;
    private GridView mGridView;
    private ImageGridAdapter mAdapter;
    private HashMap<String, ImageItem> selectedImgs = new HashMap<>();

    private int requestCode;

    private ImageView mBack;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_choose);
        mImageChooseActivity = this;

        mDataList = (List<ImageItem>) getIntent().getSerializableExtra(
                IntentConstants.EXTRA_IMAGE_LIST);
        if (mDataList == null)
            mDataList = new ArrayList<ImageItem>();
        mBucketName = getIntent().getStringExtra(
                IntentConstants.EXTRA_BUCKET_NAME);
        if (TextUtils.isEmpty(mBucketName)) {
            mBucketName = "请选择";
        }
        availableSize = getIntent().getIntExtra(
                IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
                IntentConstants.MAX_IMAGE_SIZE);

        requestCode = getIntent().getIntExtra(IntentConstants.REQUEST_CODE, 0);

        initView();

        initListener();
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageChooseActivity.this.finish();
            }
        });
        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> paths = new ArrayList<>();
                Iterator iter = selectedImgs.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    ImageItem imageItem = (ImageItem) entry.getValue();
                    paths.add(imageItem.sourcePath);
                }

                Constant.imageListenerMap.get(requestCode).allImages(paths);
                //点击确定选择图片后，关闭图片列表页和原来的图片编辑页
                closeOtherActivity();
                finish();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageItem item = mDataList.get(i);
                if (item.isSelected) {
                    item.isSelected = false;
                    selectedImgs.remove(item.imageId);
                } else {
                    if (selectedImgs.size() >= availableSize) {
                        Toast.makeText(ImageChooseActivity.this,
                                "最多选择" + availableSize + "张图片",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.isSelected = true;
                    selectedImgs.put(item.imageId, item);
                }

                mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/"
                        + availableSize + ")");
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mFinishBtn = (Button) findViewById(R.id.finish_btn);
        mGridView = (GridView) findViewById(R.id.gridview);
        mTitleTextView = (TextView) findViewById(R.id.title_center);
        mBack = (ImageView) findViewById(R.id.title_left);

        mTitleTextView.setText(mBucketName);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList);
        mGridView.setAdapter(mAdapter);

        mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/"
                + availableSize + ")");
        mAdapter.notifyDataSetChanged();
    }


    private void closeOtherActivity() {
        if (ImageBucketChooseActivity.mImageBucketChooseActivity != null) {
            ImageBucketChooseActivity.mImageBucketChooseActivity.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}