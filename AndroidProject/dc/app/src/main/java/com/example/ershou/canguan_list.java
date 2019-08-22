package com.example.ershou;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ershou.Util.Url;
import com.example.ershou.Util.UserClient;
import com.example.ershou.adapter.CommonAdapter;
import com.example.ershou.adapter.ViewHolder;
import com.example.ershou.pojo.canguan;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class canguan_list extends Activity {
	List<canguan> list;
	ListView listView;
	EditText key;
	TextView cx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.canguan_list);
		listView = (ListView) findViewById(R.id.listView);
		key = (EditText) findViewById(R.id.key);
		cx = (TextView) findViewById(R.id.cx);
		cx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getlist();
			}
		});
		getlist();
	}

	public void getlist() {
		RequestParams ps = new RequestParams();
		if (key.getText().length() > 0)
			ps.add("key", key.getText().toString());
		UserClient.get("main/getcg", ps, new AsyncHttpResponseHandler() {
			@Override
			@Deprecated
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				list = JSON.parseArray(content, canguan.class);
				listView.setAdapter(new CommonAdapter<canguan>(
						canguan_list.this, list, R.layout.canguan_item) {

					@Override
					public void convert(ViewHolder helper, canguan item) {
						// TODO Auto-generated method stub
						helper.setText(R.id.name, item.getName());
						helper.setText(R.id.address, item.getAddress());
						helper.setText(R.id.tel, "电话:" + item.getTel());
						helper.setText(R.id.xj, "星级:" + item.getXj());
						helper.setText(R.id.kw, "口味:" + item.getKw());
						ImageView pic = helper.getView(R.id.pic);
						ImageLoader.getInstance().displayImage(
								Url.url() + "upload/" + item.getPic(), pic);

					}
				});
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						startActivity(new Intent(canguan_list.this,
								FragmentTab.class).putExtra("cid",
								list.get(arg2).getId()));
					}
				});
				listView.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						startActivity(new Intent(canguan_list.this,
								pl_list.class).putExtra("cid", list.get(arg2)
								.getId()));
						return true;
					}
				});
			}
		});

	}

}
