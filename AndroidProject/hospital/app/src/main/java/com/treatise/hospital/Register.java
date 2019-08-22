package com.treatise.hospital;

/***
 *
 * 进行预约
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends AppCompatActivity {
    Set<DoctorSelect> doctorselect = new HashSet<DoctorSelect>();//存放已经选择的医生

    String[] doctorname = {"储医生 ", "夏医生 ", "李医生 ", "尹医生 ", "杨医生 ", "谢医生 "};
    String[] doctorprice = {" 20.00", " 30.00", " 40.00", " 50.00", " 60.00", " 70.00"};
    int[] doctorimgs = new int[]{R.drawable.pt1, R.drawable.pt2, R.drawable.pt3, R.drawable.pt4, R.drawable.pt5, R.drawable.pt6};
    ListView listview;

    //生成动态数组，存放数据
    ArrayList<HashMap<String, Object>> mylist =
            new ArrayList<HashMap<String, Object>>();

    Button raidersBtn;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        ButterKnife.bind(this);


        listview = (ListView) findViewById(R.id.listView1);
        getData();//调用mydata方法，将mylist写入
        //新建一个适配器
        MyAdapter myadapter = new MyAdapter(this);
        //listview控件装载适配器
        listview.setAdapter(myadapter);

    }

    //getData方法的功能：将数据存放入mylist
    private void getData() {
        for (int i = 0; i < doctorname.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemName", doctorname[i]);
            map.put("ItemPrice", doctorprice[i]);
            map.put("ItemImage", doctorimgs[i]);
            mylist.add(map);
        }
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                //查看挂号
                Intent intent = new Intent(Register.this, ListActivity.class);
                intent.putExtra("list", (Serializable) doctorselect);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent i = new Intent(Register.this, QueryDoctorActivity.class);
                startActivity(i);
                break;
            case R.id.button3:
                Intent in = new Intent(Register.this, QueryActivity.class);
                startActivity(in);
                break;
            case R.id.button4:
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
                break;
        }
    }


    //编写特定的适配器类，必须继承已有的基本适配器类
    class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;//获得视图

        //与上下文 即当前的活动关联起来
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mylist.size();//size方法是干什么的->获取元素个数
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        //在listview中展示一个view 元素
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //优化listview
            ViewHolder holder = null;//准备存放一行的四个控件
            if (convertView == null) {
                holder = new ViewHolder();
                //可以理解为从vlist获取view 之后把view返回给ListView
                convertView = mInflater.inflate(R.layout.mylistitem, null);

                holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
                holder.name = (TextView) convertView.findViewById(R.id.textView1);
                holder.price = (TextView) convertView.findViewById(R.id.textView2);
                holder.viewBtn = (Button) convertView.findViewById(R.id.button1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.image.setImageResource(doctorimgs[position]);
            holder.name.setText((String) mylist.get(position).get("ItemName"));
            holder.price.setText((String) mylist.get(position).get("ItemPrice"));
            holder.viewBtn.setTag(position);
            //给Button添加单击事件 添加Button之后ListView将失去焦点
            //需要的直接把Button的焦点去掉  android:focusable="false"
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    DoctorSelect newdoctor = new DoctorSelect();
                    newdoctor.name = mylist.get(position).get("ItemName").toString();
                    newdoctor.price = mylist.get(position).get("ItemPrice").toString();
                    newdoctor.image = doctorimgs[position];
                    Toast.makeText(getApplicationContext(), "已预约成功", Toast.LENGTH_LONG).show();
                    doctorselect.add(newdoctor);//将选中的序号存入set中

                }
            });
            return convertView;
        }

    }

    //内部类，与每一行数据对应
    class ViewHolder {
        public ImageView image;//图片
        public TextView name; //名医
        public TextView price; //手术费
        public Button viewBtn; //确定预约按钮
    }

}
