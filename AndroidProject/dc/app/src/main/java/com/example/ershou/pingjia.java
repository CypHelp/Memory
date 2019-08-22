package com.example.ershou;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ershou.Util.HttpUtils;
import com.example.ershou.Util.Url;


public class pingjia extends Activity {
	TextView tj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pingjia);
		tj = (TextView) findViewById(R.id.tj);
		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// 提交
	private class pj extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// Simulates a background job.
			String result = null;
			try {
				result = HttpUtils.doGet(Url.url() + "news/pj/" + params[0]
						+ "-" + URLEncoder.encode(params[1], "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("1"))
				startActivity(new Intent(pingjia.this, success.class));
		}
	}
}
