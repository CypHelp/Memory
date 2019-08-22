package com.treatise.hospital;
/***
 *
 * 查看医生
 *
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.treatise.hospital.adapter.TextImageAdapter;

//显示详情
public class QueryDoctorActivity extends AppCompatActivity {

	com.treatise.hospital.sqllite.DBHelper dbHelper;
	//展示的文字
	private String[] texts = new String[]{"储医生 急诊医生特指的是急诊专科医生， ",
			"夏医生 急诊医生特指的是急诊专科医生",
			"李医生  急诊医生特指的是急诊专科医生，",
			"尹医生 急诊医生特指的是急诊专科医生，",
			"杨医生 急诊医生特指的是急诊专科医生， ",
			"谢医生 急诊医生特指的是急诊专科医生，"};
	private int[] images = new int[]{R.drawable.pt1, R.drawable.pt2, R.drawable.pt3, R.drawable.pt4, R.drawable.pt5, R.drawable.pt6};

	ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//设置ListView作为显示
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check);

		mListView = (ListView) findViewById(R.id.list_image);
		TextImageAdapter adapter = new TextImageAdapter(this, texts, images);
		mListView.setAdapter(adapter);
		dbHelper = new com.treatise.hospital.sqllite.DBHelper(this);
	}
}
//		//查询数据，获取游标
//		doctor_cursor();
//
//		final AlertDialog.Builder builder=new AlertDialog.Builder(this);
//
//		//设置ListView单击监听器
//		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				final long temp=arg3;
//				builder.setMessage("真的要删除该记录吗？").setPositiveButton("是", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int which) {
//						//删除数据
//						dbHelper.del((int) temp);
//						doctor_cursor();
//					}
//				}).setNegativeButton("否", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int which) {
//
//					}
//				});
//				AlertDialog dialog=builder.create();
//				dialog.show();
//			}
//		});
//		dbHelper.close();
//	}

//	private void doctor_cursor(){
//		//查询数据，获取游标
//		Cursor cursor=dbHelper.query();
//		//列表项数据
//		String[] from={"_id","name","information"};
//		//列表项ID
//		int[] to ={R.id.text0,R.id.text1,R.id.text2,R.id.text3};
//		//适配器
//		SimpleCursorAdapter adapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.user,cursor,from,to);
//		//为列表视图添加适配器
//		mListView.setAdapter(adapter);
//	}
//}
