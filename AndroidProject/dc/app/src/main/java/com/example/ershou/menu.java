package com.example.ershou;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ershou.Util.SharedPreferencesUtils;
import com.example.ershou.adapter.MyBaseActivity;
import com.example.ershou.zxing.activity.CaptureActivity;

public class menu extends MyBaseActivity {

    LinearLayout sm;
    @Override
    protected void initUI() {
        setContentView(R.layout.menu);
        sm=findViewById(R.id.sm);
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getExtras().getString("result");
                SharedPreferencesUtils.setParam(menu.this,"zh",content);
                startActivity(new Intent(menu.this,FragmentTab.class));
            }
        }
    }
}
