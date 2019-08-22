package com.treatise.hospital;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Adminstrator
 */
public class QueryActivity extends AppCompatActivity {
    ListView listView;
    com.treatise.hospital.sqllite.DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView= (ListView) findViewById(R.id.music_listview);
        this.setTitle("浏览用户列表信息");
        dbHelper=new com.treatise.hospital.sqllite.DBHelper(this);
        //查询数据，获取游标
        use_cursor();

        //提示对话框
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);

        //设置ListView单击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final long temp=arg3;
                builder.setMessage("真的要删除该记录吗？").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //删除数据
                        dbHelper.del((int) temp);
                        use_cursor();
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        dbHelper.close();
    }
    private void use_cursor(){
        //查询数据，获取游标
        Cursor cursor=dbHelper.query();
        //列表项数据
        String[] from={"_id","name","password","address"};
        //列表项ID
        int[] to ={R.id.text0,R.id.text1,R.id.text2,R.id.text3};
        //适配器
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.user,cursor,from,to);
        //为列表视图添加适配器
        listView.setAdapter(adapter);
    }
}
