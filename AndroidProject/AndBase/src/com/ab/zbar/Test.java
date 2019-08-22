package com.ab.zbar;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import com.ab.util.AbToastUtil;

/**
 * Created by wolf on 2015/12/8.
 */
public class Test extends Activity {
    void use() {
        try {

            Intent intent = new Intent();
            intent.setClass(this, ScanCaptureAct.class);
            startActivityForResult(intent, 30);

        } catch (Exception e) {
            Toast.makeText(this, "相机打开失败,请检查相机是否可正常使用", Toast.LENGTH_LONG);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 30:
                if (resultCode == RESULT_OK) {
                    String contents = intent.getStringExtra("SCAN_RESULT");
                    AbToastUtil.showToast(this, "扫描结果:" + contents);
                } else if (resultCode == RESULT_CANCELED) {
                    // Handle cancel
                    Toast.makeText(this, "扫描失败", Toast.LENGTH_SHORT);
                }
                break;
        }

    }
}
