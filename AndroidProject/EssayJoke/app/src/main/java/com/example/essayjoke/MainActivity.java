package com.example.essayjoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.haha.baselibrary.ioc.OnClick;
import com.haha.baselibrary.ioc.ViewById;
import com.haha.baselibrary.ioc.ViewUitls;


public class MainActivity extends AppCompatActivity {

    /****Hello World!****/
    @ViewById(R.id.textTv)
    private TextView mTextTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUitls.inject(this);

    }

    @OnClick(R.id.textTv)
    private void textTvClick(TextView textTv) {
        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
    }

}
