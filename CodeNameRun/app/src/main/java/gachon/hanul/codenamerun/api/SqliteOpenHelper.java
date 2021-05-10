package gachon.hanul.codenamerun.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class SqliteOpenHelper extends SQLiteOpenHelper{

    public SqliteOpenHelper (Context context , String name,SQLiteDatabase.CursorFactory factory , int version ) {
        super(context, name, factory, version);
    }
    public void onCreate( SQLiteDatabase db ) {
        String usql = "create table if not exists user(id varchar(32) PRIMARY KEY , password varchar(128) not null, name varchar(32) not null, height Integer not null, weight Integer not null, sex varchar(10));";
        db.execSQL(usql);
    }
    public void onUpgrade (SQLiteDatabase db , int oldVersion , int newVersion ) {
        String sql =" drop table if exists user";
        db.execSQL (sql);
        onCreate(db);
    }
}