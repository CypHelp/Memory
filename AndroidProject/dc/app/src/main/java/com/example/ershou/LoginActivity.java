package com.example.ershou;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ershou.Util.HttpUtils;
import com.example.ershou.Util.MyApplication;
import com.example.ershou.Util.SharedPreferencesUtils;
import com.example.ershou.Util.Url;


public class LoginActivity extends Activity {
	private ImageView loginImage;
	private TextView regist;
	private TextView topText;
	private TextPaint tp;
	private Button loginbtn;
	private Button jinru;
	private EditText username;
	private EditText password;
	private Drawable mIconPerson;
	private Drawable mIconLock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);// 不随屏幕旋转
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login);
		regist=(TextView)findViewById(R.id.regist);
		regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			startActivity(new Intent(LoginActivity.this,RegistActivity.class));
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
		mIconPerson = getResources().getDrawable(R.drawable.txt_person_icon);
		mIconPerson.setBounds(5, 1, 60, 50);
		mIconLock = getResources().getDrawable(R.drawable.txt_lock_icon);
		mIconLock.setBounds(5, 1, 60, 50);

		username = (EditText) findViewById(R.id.username);
		username.setCompoundDrawables(mIconPerson, null, null, null);
		password = (EditText) findViewById(R.id.pass);
		password.setCompoundDrawables(mIconLock, null, null, null);
		loginbtn=(Button)findViewById(R.id.loginbtn);
		init();

	}

	@SuppressWarnings("deprecation")
	public void init() {
		topText = (TextView) findViewById(R.id.topname);
		topText.setTextColor(Color.MAGENTA);
		topText.setTextSize(24.0f);
		topText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
		// 使用TextPaint的仿“粗体�?设置setFakeBoldText为true。目前还无法支持仿�?斜体”方�?
		tp = topText.getPaint();
		tp.setFakeBoldText(true);
//		loginImage = (ImageView) findViewById(R.id.loginImage);
//		loginImage.setBackgroundDrawable(new BitmapDrawable(Util.toRoundBitmap(
//				this, "putao2.jpg")));
//		loginImage.getBackground().setAlpha(0);
//		loginImage.setImageBitmap(Util.toRoundBitmap(this, "putao2.jpg"));

		loginbtn = (Button) findViewById(R.id.loginbtn);
		loginbtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.getBackground().setAlpha(20);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.getBackground().setAlpha(255);// 设置的�?明度
					userlogin u=new userlogin();
					u.execute(username.getText().toString(),password.getText().toString());
				}
				return true;
			}

		});

	}

	/**
	 * 注册
	 * 
	 * @author Administrator
	 * 
	 */
	public class userlogin extends AsyncTask<String, Void, String> {

		 ProgressDialog p=new ProgressDialog(LoginActivity.this,
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
			result= HttpUtils.doGet(Url.url()+"user/ulogin?"+arg0[0]+"-"+arg0[1]);

			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			p.dismiss();
					if(result.equals("1"))
						Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
					else{
						try {
							JSONObject j=new JSONObject(result);
							User u=new User();
							u.setPass(j.getString("pass"));
							u.setUsername(j.getString("username"));
							u.setId(j.getString("id"));
							u.setNickname(j.getString("nickname"));
							SharedPreferencesUtils.setParam(LoginActivity.this, "nickname", u.getNickname());
							SharedPreferencesUtils.setParam(LoginActivity.this, "id", u.getId());
							SharedPreferencesUtils.setParam(LoginActivity.this, "username", u.getUsername());
							Intent i=new Intent(LoginActivity.this,menu.class);
							MyApplication.getApp().setUser(u);
							Bundle b=new Bundle();
							b.putSerializable("u", u);
							i.putExtras(b);
							startActivity(i);
							finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		}

	}
}
