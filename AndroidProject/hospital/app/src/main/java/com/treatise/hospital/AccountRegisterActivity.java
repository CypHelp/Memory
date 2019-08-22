package com.treatise.hospital;
/***
 *
 * 用户注册
 *
 */

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.treatise.hospital.sqllite.DBHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountRegisterActivity extends AppCompatActivity {


    @BindView(R.id.usename)
    EditText usenameEt;
    @BindView(R.id.password)
    EditText passwordEt;
    @BindView(R.id.okpassword)
    EditText okpasswordEt;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.male)
    RadioButton maleRbtn;
    @BindView(R.id.female)
    RadioButton femaleRbtn;
    @BindView(R.id.address)
    EditText addressEd;
    @BindView(R.id.sex)
    RadioGroup sexRG;

    final String FILE_NAME = "/ypSD.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_register);
        ButterKnife.bind(this);

        //焦点事件
        usenameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String usename = usenameEt.getText().toString();
                if (usename.length() == 0) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usename.length() < 7) {
                    Toast.makeText(getApplicationContext(), "用户名至少7位", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        passwordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pass = ((EditText) v).getText().toString();
                    if (pass.length() < 7) {
                        Toast.makeText(getApplicationContext(), "密码长度至少7位", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        okpasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pass = ((EditText) v).getText().toString();
                    if (pass.length() < 7) {
                        Toast.makeText(getApplicationContext(), "密码长度至少7位", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        sexRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String trip = checkedId == R.id.male ? "性别：男" : "性别：女";
                write(trip);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename = usenameEt.getText().toString();
                String pass = passwordEt.getText().toString();
                String repass = okpasswordEt.getText().toString();
                String address=addressEd.getText().toString();

                if (usename.length() == 0) {
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usename.length() < 7) {
                    Toast.makeText(getApplicationContext(), "用户名至少7位", Toast.LENGTH_LONG).show();
                    return;
                }

                if (pass.length() < 7 || repass.length() < 7) {
                    Toast.makeText(getApplicationContext(), "密码长度至少7位", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!pass.equals(repass)) {
                    Toast.makeText(getApplicationContext(), "密码两次不一样", Toast.LENGTH_LONG).show();
                    return;
                }

                //创建对象，封装记录信息
                ContentValues values = new ContentValues();
                values.put("name", usename);
                values.put("password", pass);
                values.put("address",address);
                //创建数据库工具类
                DBHelper helper = new DBHelper(getApplicationContext());
                helper.insert(values);

                write(usename);
                write(pass);
                write(address);
                Toast.makeText(getApplicationContext(), "succcess", Toast.LENGTH_LONG);

                Intent intent = new Intent(AccountRegisterActivity.this, Register.class);
                startActivity(intent);
            }
        });


    }

    public void write(String content) {
        try {
            //如果手机插入了SD卡，而且应用程序具有访问SD卡权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                获取SD卡目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targeFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);

                RandomAccessFile raf = new RandomAccessFile(targeFile, "rw");
                raf.seek(targeFile.length());

//                输出
                raf.write(content.getBytes());
                raf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            //如果手机插入了SD卡，而且应用程序具有访问SD卡权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获取SD卡目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder("");
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


