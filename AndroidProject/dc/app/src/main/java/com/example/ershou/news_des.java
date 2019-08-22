package com.example.ershou;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ershou.Util.Url;
import com.example.ershou.Util.news;
import com.nostra13.universalimageloader.core.ImageLoader;

public class news_des extends Activity {
    TextView title;
    TextView mes, price;
    ImageView img;
    news n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_des);
        mes = (TextView) findViewById(R.id.msg);
        price = (TextView) findViewById(R.id.price);
        title = (TextView) findViewById(R.id.title);
        img = (ImageView) findViewById(R.id.img);
        n = (news) getIntent().getExtras().getSerializable("news");
        mes.setText(n.getMsg());
        price.setText("价位:" + n.getPrice());
        title.setText(n.getTitle());
        gettouxiang g = new gettouxiang(img);
        g.execute(Url.url() + "upload/" + n.getImg());
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
