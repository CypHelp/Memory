package com.treatise.hospital;

/***
 *
 * 查看预约
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListActivity extends android.app.ListActivity {

	private ArrayList<HashMap<String, Object>> mylist;
	MyAdapter adapter = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Set<DoctorSelect> lt = (Set<DoctorSelect>) getIntent().getSerializableExtra("list");
		ArrayList<HashMap<String,Object>> mylists = new ArrayList<HashMap<String,Object>>();
		for (DoctorSelect td : lt) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", td.name);
			map.put("price", td.price);
			map.put("img", td.image);
			mylists.add(map);
		}
		mylist = mylists;
		adapter = new MyAdapter(this);
		setListAdapter(adapter);
	}

	public void del(final int position) {
		new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("确定取消挂号吗？")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mylist.remove(position);
						setListAdapter(adapter);
					}
				}).show();
	}


	public static final class ViewHolder {
		public ImageView image;
		public TextView name;
		public TextView price;
		public Button viewBtn;
	}

	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mylist.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.mylistitem1, null);

				holder.image = convertView
						.findViewById(R.id.imageView5);
				holder.name = convertView
						.findViewById(R.id.textView5);
				holder.price = convertView
						.findViewById(R.id.textView6);
				holder.viewBtn = convertView
						.findViewById(R.id.button5);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setBackgroundResource((Integer) mylist.get(position).get("img"));
			holder.name.setText((String) mylist.get(position).get("name"));
			holder.price.setText((String) mylist.get(position).get("price"));
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					del(position);
				}
			});
			return convertView;
		}
	}



}



