package com.treatise.hospital;
/***
 *
 * 登录
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.treatise.hospital.sqllite.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity  {
    private static final String TAG = "login";

    @BindView(R.id.hrs_log)
    RelativeLayout hrsLog;
    @BindView(R.id.titles)
    TextView titles;
    @BindView(R.id.useridEt)
    EditText useridEt;
    @BindView(R.id.passEt)
    EditText passEt;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.registBtn)
    Button registBtn;
    @BindView(R.id.fastloginBtn)
    Button fastloginBtn;
    @BindView(R.id.l3)
    LinearLayout l3;
    @BindView(R.id.promptText)
    TextView promptText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.loginBtn, R.id.registBtn, R.id.fastloginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                //登录
                String userid = useridEt.getText().toString().trim();
                String pass = passEt.getText().toString().trim();
                if (userid.equals("")) {
                    promptText.setText(R.string.userIdError);
                    return;
                }
                if (pass.equals("")) {
                    promptText.setText(R.string.passError);
                    return;
                }
                if (userid.length() >= 7 && pass.length() >= 7) {
                    Toast.makeText(getApplicationContext(), R.string.loginSuccess, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Register.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_LONG).show();
                    //
                }
                break;
            case R.id.registBtn:
                //注册
                Intent i=new Intent(MainActivity.this,AccountRegisterActivity.class);
                startActivity(i);
                break;
            case R.id.fastloginBtn:
                //游客登录
                Toast.makeText(getApplicationContext(), R.string.loginSuccess, Toast.LENGTH_LONG).show();
                //从MainActivity->SideActivity
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                break;
        }
    }
}

