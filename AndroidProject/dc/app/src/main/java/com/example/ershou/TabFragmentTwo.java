package com.example.ershou;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ershou.Util.DBHelper;
import com.example.ershou.Util.HttpUtils;
import com.example.ershou.Util.SharedPreferencesUtils;
import com.example.ershou.Util.UUID;
import com.example.ershou.Util.Url;
import com.example.ershou.Util.news;


/**
 * 
 * @author yummy email:yummyl.lau@gmail.com
 */
public class TabFragmentTwo extends Fragment {
	ListView listview;
	List<news> l;
	TextView tj;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.tabfragmenttwo, container,
				false);

		tj = (TextView) parentView.findViewById(R.id.tj);
		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (SharedPreferencesUtils.getParam(getActivity(),"zh","").toString().equals("")) {
					Toast.makeText(getActivity(), "请选择桌号", Toast.LENGTH_SHORT)
							.show();
					getActivity().finish();
				} else {
					String ss = UUID.getUuid();
					double price=0;
					for (int i = 0; i < l.size(); i++) {
						price=price+Double.parseDouble(l.get(i).getPrice());
						new tijiao().execute(
								SharedPreferencesUtils.getParam(getActivity(),
										"id", "").toString(), l.get(i)
										.getTime(), ss, SharedPreferencesUtils.getParam(getActivity(),"zh","").toString(),l.get(i).getCount());
					}
					DBHelper d = new DBHelper(getActivity());
					d.delAllData("s");
					d.close();
					price=0;
					  Random random = new Random(100);
					int a=random.nextInt();
					if(a==1){
						new AlertDialog.Builder(getActivity()).setTitle("恭喜您获得免单的机会").setPositiveButton("确定", null).show();
					}else{
						new AlertDialog.Builder(getActivity()).setTitle("支付")
						.setMessage("请支付" + price + "元")
						.setPositiveButton("支付宝", null)
						.setNegativeButton("微信", null).show();
					}
				}

			}
		});
		listview = (ListView) parentView.findViewById(R.id.listView);
		DBHelper d = new DBHelper(getActivity());
		l = d.findAll();
		d.close();
		if (l != null) {
			System.out.println(l.size());
			listview.setAdapter(new gwc_adapter());
		}
		return parentView;
	}

	// 提交
	private class tijiao extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// Simulates a background job.
			String result = null;
			try {
				result = HttpUtils.doGet(Url.url() + "news/adddingdan/" + params[0]
						+ "-" + params[1] + "-" + params[2]+"-"+URLEncoder.encode(params[3], "utf-8")+"-"+params[4]);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
	}

	public class gwc_adapter extends BaseAdapter {

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
		public View getView(final int arg0, View v, ViewGroup arg2) {
			// TODO Auto-generated method stub
			v = LayoutInflater.from(getActivity()).inflate(R.layout.gwc_item,
					null);
			TextView name = (TextView) v.findViewById(R.id.name);
			TextView price = (TextView) v.findViewById(R.id.price);
			TextView del = (TextView) v.findViewById(R.id.del);
			TextView count = (TextView) v.findViewById(R.id.count);
			del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg) {
					// TODO Auto-generated method stub
					DBHelper dd = new DBHelper(getActivity());
					dd.del(l.get(arg0).getId());
					l = dd.findAll();
					listview.setAdapter(new gwc_adapter());
				}
			});
			name.setText(l.get(arg0).getTitle());
			price.setText(l.get(arg0).getPrice()+"元");
			count.setText(l.get(arg0).getCount());
			return v;
		}

	}
}
