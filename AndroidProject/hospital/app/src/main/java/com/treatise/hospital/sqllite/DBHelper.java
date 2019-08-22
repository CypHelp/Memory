package com.treatise.hospital.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adminstrator
 */
public class DBHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String DB_NAME="Person.db";
    //表名
    private static final String TBL_NAME="PersonTbl";
    private static final String TBL_DOCNAME="DoctorTbl";
    //声明SQLiteDatabase对象
    private SQLiteDatabase db;
    public DBHelper(Context c) {
        super(c, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //获取SQLiteDatabase对象
        this.db=db;
        //创建表
        String CREATE_TBL= "create table PersonTbl(_id integer primary key autoincrement,name text,password text,address text)";
        db.execSQL(CREATE_TBL);

        String sql= "create table DoctorTbl(_id integer primary key autoincrement,name text,information text)";
        db.execSQL(sql);
    }
    //插入
    public void insert(ContentValues values){
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TBL_NAME,null,values);
        db.insert(TBL_DOCNAME,null,values);
        db.close();
    }

    //查询用户
    public Cursor query(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query(TBL_NAME,null,null,null,null,null,null);
        return  c;
    }

    //查询医生
    public Cursor docquery(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query(TBL_DOCNAME,null,null,null,null,null,null);
        return c;
    }

    //删除
    public void del(int id){
        if(db==null)
            db=getWritableDatabase();
        db.delete(TBL_NAME,"_id=?",new String[]{String.valueOf(id)});
        db.delete(TBL_DOCNAME,"_id=?",new String[]{String.valueOf(id)});
    }

    //关闭数据库
    public void close(){
        if(db !=null)
            db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
