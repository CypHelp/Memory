package com.example.ershou;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ershou.Util.DBHelper;
import com.example.ershou.Util.Url;
import com.example.ershou.Util.news;
import com.nostra13.universalimageloader.core.ImageLoader;

public class news_adapter extends BaseAdapter {

	private List<news> l;
	private Context con;

	public news_adapter(List<news> ll, Context c) {
		this.con = c;
		this.l = ll;
	}

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
		v = LayoutInflater.from(con).inflate(R.layout.news_item, null);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView price = (TextView) v.findViewById(R.id.price);
		price.setText(l.get(arg0).getPrice() + "元");
		TextView type = (TextView) v.findViewById(R.id.type);
		type.setText(l.get(arg0).getType()+"--"+l.get(arg0).getState());
		TextView msg = (TextView) v.findViewById(R.id.msg);
		title.setText(l.get(arg0).getTitle());
		msg.setText(l.get(arg0).getMsg());
		ImageView img = (ImageView) v.findViewById(R.id.img);
		ImageView add = (ImageView) v.findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg) {
				// TODO Auto-generated method stub
				final EditText e=new EditText(con);
				e.setHint("菜品数目");
				new AlertDialog.Builder(con).setTitle("填写数目").setView(e).setPositiveButton("确定",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dd, int arg1) {
						// TODO Auto-generated method stub
						DBHelper d = new DBHelper(con);
						d.insertProduce(l.get(arg0).getId(), l.get(arg0).getTitle(), l
								.get(arg0).getPrice(), l.get(arg0).getName(),
								l.get(arg0).getTel(),e.getText().toString());
						d.close();
						Toast.makeText(con, "添加成功", Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton("取消", null).show();
				
			}
		});
		gettouxiang g = new gettouxiang(img);
		g.execute(Url.url() + "upload/" + l.get(arg0).getImg());
		return v;
	}

	// 获取网络图片
	public class gettouxiang extends AsyncTask<String, Void, Bitmap> {

		private ImageView img;

		public gettouxiang(ImageView i) {
			this.img = i;
		}

		@Override
		protected Bitmap doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			return ImageLoader.getInstance().loadImageSync(arg0[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result != null) {
				if (img.getId() == R.id.img) {
					img.setImageBitmap(result);
				} else {
					img.setImageBitmap(result);
				}
			}

		}
	}
}
