package com.example.ershou;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ershou.Util.HttpUtils;
import com.example.ershou.Util.MyApplication;
import com.example.ershou.Util.MyToastUtil;
import com.example.ershou.Util.SharedPreferencesUtils;
import com.example.ershou.Util.Url;
import com.example.ershou.Util.UserClient;
import com.example.ershou.Util.des;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 */
public class TabFragmantThree extends Fragment {

	List<des> l;
	ListView listView;
	TextView djs;
	Long leftTime= Long.valueOf(1000);
	Handler handler = new Handler();
	Runnable update_thread = new Runnable() {
		@Override
		public void run() {
			leftTime--;
			if (leftTime > 0) {
				//倒计时效果展示
				String formatLongToTimeStr = formatLongToTimeStr(leftTime);
				djs.setText(formatLongToTimeStr);
				//每一秒执行一次
				handler.postDelayed(this, 1000);
			} else {//倒计时结束
				//处理业务流程

				//发送消息，结束倒计时
				Message message = new Message();
				message.what = 1;
				handlerStop.sendMessage(message);
			}
		}
	};
	final Handler handlerStop = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					leftTime = Long.valueOf(0);
					handler.removeCallbacks(update_thread);
					break;
			}
			super.handleMessage(msg);
		}

	};
	public String formatLongToTimeStr(Long l) {
		int hour = 0;
		int minute = 0;
		int second = 0;
		second = l.intValue() ;
		if (second > 60) {
			minute = second / 60;   //取整
			second = second % 60;   //取余
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		String strtime = "剩余取餐时间："+minute+"分"+second+"秒";
		return strtime;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		leftTime = Long.valueOf(0);
		handler.removeCallbacks(update_thread);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.tabfragmentthree,
				container, false);
		djs=parentView.findViewById(R.id.djs);

		listView=(ListView)parentView.findViewById(R.id.listView);
		new getAll().execute(SharedPreferencesUtils.getParam(getActivity(),"id","").toString());
		return parentView;
	}
	// 提交
		private class getAll extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				// Simulates a background job.
				String result = null;
				result = HttpUtils.doGet(Url.url() + "news/findAllDingdans/" + params[0]);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if(!result.equals("1")){
				   l=JSON.parseArray(result, des.class);
				   if(l.size()>0) {
					   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					   try {
						   leftTime = 1800-(new Date().getTime() - df.parse(l.get(l.size()-1).getTime()).getTime()) / 1000;
					   } catch (ParseException e) {
						   e.printStackTrace();
					   }
					   handler.postDelayed(update_thread, 1000);
					   listView.setAdapter(new dd_adapter());

				   }
				}
			}
		}
		public class dd_adapter extends BaseAdapter{

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return l.size();
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return l.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public View getView(int arg0, View v, ViewGroup arg2) {
				// TODO Auto-generated method stub
				v=LayoutInflater.from(getActivity()).inflate(R.layout.dd_item, null);
				TextView text=(TextView)v.findViewById(R.id.text);
				text.setText(l.get(arg0).getName());
				TextView count=(TextView)v.findViewById(R.id.count);
				count.setText(arg0+1+"");
				return v;
			}
			
		}
}
