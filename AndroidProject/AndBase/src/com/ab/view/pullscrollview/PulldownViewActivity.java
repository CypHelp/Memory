package com.ab.view.pullscrollview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import com.ab.R;

/**
 * Pull down ScrollView demo.
 *
 * @author markmjw
 * @date 2014-04-30
 */
public class PulldownViewActivity extends Activity implements PullScrollView.OnTurnListener {
    private PullScrollView mScrollView;
    private ImageView mHeadImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_scroll_view);

        initView();
    }

    protected void initView() {
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);


        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }


    @Override
    public void onTurn() {

    }

}
