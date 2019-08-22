package com.ab.view.lock;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.ab.R;

public class GuideGesturePasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.gesturepassword_guide);
        findViewById(R.id.gesturepwd_guide_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                App.getInstance().getLockPatternUtils().clearLock();
//                App.getInstance().getLockPatternUtils().clearLock();
                Intent intent = new Intent(GuideGesturePasswordActivity.this,
                        CreateGesturePasswordActivity.class);
                // 打开新的Activity
                startActivity(intent);
                finish();
            }
        });
    }

}
