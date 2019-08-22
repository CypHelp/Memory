package com.example.ershou;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.ershou.Util.MyApplication;
import com.example.ershou.Util.UserClient;
import com.example.ershou.adapter.CommonAdapter;
import com.example.ershou.adapter.ViewHolder;
import com.example.ershou.pojo.pinglun;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class pl_list extends Activity{
	Button tijiao;
	EditText plnr;
	ListView listView;
	List<pinglun> list;
	String hfid = "", hfname = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pl_list);
		listView = (ListView) findViewById(R.id.listView);
		tijiao = (Button) findViewById(R.id.fb);
		plnr = (EditText) findViewById(R.id.plnr);

		tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
					RequestParams ps = new RequestParams();
					ps.add("pinglun.mid", getIntent().getStringExtra("cid"));
					ps.add("pinglun.uid", MyApplication.getApp().getUser()
							.getId());
					ps.add("pinglun.uname", MyApplication.getApp().getUser()
							.getNickname());
					ps.add("pinglun.msg", plnr.getText().toString());
					ps.add("pinglun.hfuid", hfid);
					ps.add("pinglun.hfuname", hfname);
					UserClient.get("main/addpinglun", ps,
							new AsyncHttpResponseHandler() {
								@Override
								@Deprecated
								public void onSuccess(int statusCode,
										String content) {
									// TODO Auto-generated method stub
									super.onSuccess(statusCode, content);
									plnr.setText("");
									getpl();
								}
							});
				
			}
		});

	}
	
	public void getpl() {
		RequestParams ps = new RequestParams();
		ps.add("mid", getIntent().getStringExtra("cid"));
		UserClient.get("main/findplbymid", ps, new AsyncHttpResponseHandler() {
			@Override
			@Deprecated
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				list = JSON.parseArray(content, pinglun.class);
				listView.setAdapter(new CommonAdapter<pinglun>(pl_list.this,
						list, R.layout.pl_item) {
					@Override
					public void convert(ViewHolder helper, pinglun item) {
						// TODO Auto-generated method stub
						if (item.getHfuname() == null
								|| item.getHfuname().length() == 0)
							helper.setText(R.id.uname, item.getUname());
						else
							helper.setText(R.id.uname, item.getUname() + "回复"
									+ item.getHfuname());
						helper.setText(R.id.msg, item.getMsg());
					}
				});
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							final int position, long id) {
						new AlertDialog.Builder(pl_list.this)
								.setTitle("删除评论")
								.setPositiveButton("删除",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												RequestParams ps = new RequestParams();
												ps.add("id", list.get(position)
														.getId());
												UserClient
														.get("main/delpl",
																ps,
																new AsyncHttpResponseHandler() {
																	@Override
																	public void onSuccess(
																			String content) {
																		super.onSuccess(content);
																		getpl();
																	}
																});
											}
										})
								.setNegativeButton("回复",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												hfid = list.get(position)
														.getUid();
												hfname = list.get(position)
														.getUname();
											}
										}).show();
					}
				});
			}
		});
	}

}
