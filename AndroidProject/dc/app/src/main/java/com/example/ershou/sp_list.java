package com.example.ershou;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ershou.Util.MyApplication;
import com.example.ershou.Util.MyToastUtil;
import com.example.ershou.Util.Url;
import com.example.ershou.Util.UserClient;
import com.example.ershou.Util.news;
import com.example.ershou.adapter.CommonAdapter;
import com.example.ershou.adapter.ViewHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class sp_list extends Activity {

	List<news> list;
	ListView listView;
	TextView gm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sp_list);
		listView = (ListView) findViewById(R.id.listView);
		gm = (TextView) findViewById(R.id.gm);
		RequestParams ps = new RequestParams();
		ps.add("did", getIntent().getStringExtra("did"));
		UserClient.get("main/getsp", ps, new AsyncHttpResponseHandler() {
			@Override
			@Deprecated
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list = JSON.parseArray(content, news.class);
				listView.setAdapter(new CommonAdapter<news>(sp_list.this, list,
						R.layout.sp_item) {

					@Override
					public void convert(ViewHolder helper, news item) {
						// TODO Auto-generated method stub
						helper.setText(R.id.title, item.getName());
						helper.setText(R.id.type, item.getType());
						helper.setText(R.id.price, item.getPrice());
						helper.setText(R.id.msg, item.getMsg());
						ImageView img = helper.getView(R.id.img);
						ImageLoader.getInstance().displayImage(
								Url.url() + "upload/" + item.getImg(), img);
					}
				});

				gm.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						double d = 0;
						for (int i = 0; i < list.size(); i++) {
							d = d + Double.parseDouble(list.get(i).getPrice());
						}
						new AlertDialog.Builder(sp_list.this).setTitle("支付")
								.setMessage("请支付" + d + "元")
								.setPositiveButton("支付宝", null)
								.setNegativeButton("微信", null).show();
						RequestParams ps = new RequestParams();
						ps.add("code", getIntent().getStringExtra("did"));
						ps.add("uid", MyApplication.getApp().getUser().getId());
						UserClient.get("main/gmzm", ps,
								new AsyncHttpResponseHandler() {
									@Override
									@Deprecated
									public void onSuccess(String content) {
										// TODO Auto-generated method stub
										super.onSuccess(content);
										MyToastUtil.ShowToast(sp_list.this,
												"成功");
									}
								});
					}
				});

			}
		});
	}

}
