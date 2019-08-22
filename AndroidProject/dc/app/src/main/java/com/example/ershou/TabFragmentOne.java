package com.example.ershou;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ershou.Util.HttpUtils;
import com.example.ershou.Util.Url;
import com.example.ershou.Util.news;

/**
 * 
 * @author yummy email:yummyl.lau@gmail.com
 */
public class TabFragmentOne extends Fragment {
	List<news> l;
	ListView listview;
	EditText name;
	TextView t1, t2, t3, t4, t5, ss;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.tabfragmentone, container,
				false);
		name = (EditText) parentView.findViewById(R.id.name);
		t1 = (TextView) parentView.findViewById(R.id.t1);
		t2 = (TextView) parentView.findViewById(R.id.t2);
		t3 = (TextView) parentView.findViewById(R.id.t3);
		t4 = (TextView) parentView.findViewById(R.id.t4);
		t5 = (TextView) parentView.findViewById(R.id.t5);
		t1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new findBytype().execute("凉菜");
				t1.setTextColor(Color.BLUE);
				t2.setTextColor(Color.BLACK);
				t3.setTextColor(Color.BLACK);
				t4.setTextColor(Color.BLACK);
				t5.setTextColor(Color.BLACK);

			}
		});
		t2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new findBytype().execute("热菜");
				t2.setTextColor(Color.BLUE);
				t1.setTextColor(Color.BLACK);
				t3.setTextColor(Color.BLACK);
				t4.setTextColor(Color.BLACK);
				t5.setTextColor(Color.BLACK);
			}
		});
		t3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new findBytype().execute("主食");
				t3.setTextColor(Color.BLUE);
				t2.setTextColor(Color.BLACK);
				t1.setTextColor(Color.BLACK);
				t4.setTextColor(Color.BLACK);
				t5.setTextColor(Color.BLACK);
			}
		});
		t4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new findBytype().execute("汤类");
				t4.setTextColor(Color.BLUE);
				t2.setTextColor(Color.BLACK);
				t3.setTextColor(Color.BLACK);
				t1.setTextColor(Color.BLACK);
				t5.setTextColor(Color.BLACK);
			}
		});
		t5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new findBytype().execute("推荐");
				t5.setTextColor(Color.BLUE);
				t2.setTextColor(Color.BLACK);
				t3.setTextColor(Color.BLACK);
				t1.setTextColor(Color.BLACK);
				t4.setTextColor(Color.BLACK);
			}
		});
		ss = (TextView) parentView.findViewById(R.id.ss);
		ss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!name.getText().toString().equals("")) {
					new findByname().execute(name.getText().toString());
				}

			}
		});
		listview = (ListView) parentView.findViewById(R.id.listView);
		new GetDataTask().execute();
		return parentView;
	}

	private class findByname extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// Simulates a background job.
			String result = null;
			try {
				result = HttpUtils.doGet(Url.url() + "news/findByName/"
						+ URLEncoder.encode(params[0], "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (!result.equals("1")) {
				l = JSON.parseArray(result, news.class);
				listview.setAdapter(new news_adapter(l, getActivity()));
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity(), news_des.class);
						Bundle b = new Bundle();
						b.putSerializable("news", l.get(arg2));
						i.putExtras(b);
						startActivity(i);
					}
				});
			} else {
				l = new ArrayList<news>();
				listview.setAdapter(new news_adapter(l, getActivity()));
			}
			super.onPostExecute(result);
		}
	}

	private class findBytype extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// Simulates a background job.
			String result = null;
			try {
				result = HttpUtils.doGet(Url.url() + "news/findByType/"
						+ URLEncoder.encode(params[0], "utf-8") );
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (!result.equals("1")) {
				l = JSON.parseArray(result, news.class);
				listview.setAdapter(new news_adapter(l, getActivity()));
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity(), news_des.class);
						Bundle b = new Bundle();
						b.putSerializable("news", l.get(arg2));
						i.putExtras(b);
						startActivity(i);
					}
				});
			} else {
				l = new ArrayList<news>();
				listview.setAdapter(new news_adapter(l, getActivity()));
			}
			super.onPostExecute(result);
		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String result = null;
			result = HttpUtils.doGet(Url.url() + "news/findAlls/");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// Do some stuff here

			// Call onRefreshComplete when the list has been refreshed.
			l = JSON.parseArray(result, news.class);
			listview.setAdapter(new news_adapter(l, getActivity()));
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), news_des.class);
					Bundle b = new Bundle();
					b.putSerializable("news", l.get(arg2));
					i.putExtras(b);
					startActivity(i);
				}
			});
			super.onPostExecute(result);
		}
	}

}
