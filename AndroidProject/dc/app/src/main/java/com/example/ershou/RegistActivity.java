package com.example.ershou;

import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ershou.Util.HttpUtils;
import com.example.ershou.Util.Url;


public class RegistActivity extends Activity {
	private LinearLayout back;
	private EditText username;
	private EditText pass;
	private EditText queding, address, tel;
	private Button login;
	private EditText nickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_regist);
		pass = (EditText) findViewById(R.id.pass);
		address = (EditText) findViewById(R.id.address);
		tel = (EditText) findViewById(R.id.tel);
		username = (EditText) findViewById(R.id.username);
		nickname = (EditText) findViewById(R.id.nickname);
		queding = (EditText) findViewById(R.id.queding);
		login = (Button) findViewById(R.id.tijiao);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (username.getText().equals("")
						|| nickname.getText().equals("")
						|| pass.getText().equals("")
						|| queding.getText().equals("")) {
					Toast.makeText(RegistActivity.this, "信息不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					if (pass.getText().toString()
							.equals(queding.getText().toString())) {
						useradd u = new useradd();
						u.execute(username.getText().toString(), pass.getText()
								.toString(), nickname.getText().toString());
					} else {
						Toast.makeText(RegistActivity.this, "两次密码输入不一致",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		back = (LinearLayout) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
	}

	/**
	 * 注册
	 * 
	 * @author Administrator
	 * 
	 */
	public class useradd extends AsyncTask<String, Void, String> {

		ProgressDialog p = new ProgressDialog(RegistActivity.this,
				ProgressDialog.STYLE_SPINNER);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			p.setMessage("Loading....");
			p.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			result = HttpUtils.doGet(Url.url() + "user/addUser/" + arg0[0]
					+ "-" + arg0[1] + "-" + URLEncoder.encode(arg0[2]) + "-"
					+ URLEncoder.encode(address.getText().toString()) + "-"
					+ URLEncoder.encode(tel.getText().toString()));
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			p.dismiss();
			if (result.equals("success")) {
				Toast.makeText(RegistActivity.this, "成功", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else {
				Toast.makeText(RegistActivity.this, "失败", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}
}
