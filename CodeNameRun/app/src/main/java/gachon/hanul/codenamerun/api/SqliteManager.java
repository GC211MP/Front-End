package gachon.hanul.codenamerun.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SqliteManager {
    SQLiteDatabase database;
    SqliteOpenHelper helper;
    public SqliteManager(Context context, String name) {
        helper = new SqliteOpenHelper(context, name, null, 1);
    }
    public static SqliteManager open(Context context, String name) {
        return new SqliteManager(context,name);
    }


    // print the all user. we can get the data using different command
    //필요한 조건으로 충분히 가능.
    public void select() {
        database = helper.getWritableDatabase();
        Cursor c = database.rawQuery("select * from user",null);
        while(c.moveToNext()) {
            String id=c.getString(0);
            String pw=c.getString(1);
            String name=c.getString(2);
            int ht=c.getInt(3);
            int wt=c.getInt(4);
            String sex=c.getString(5);
            Log.i("db1","id: "+id+" "+pw+" "+name+" "+ht+" "+wt+ " "+sex+" ");
        }
    }


    //write agent info 이후에 가능
    public boolean insert(SqliteDto sdto) {
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", sdto.getId());
            values.put("password", sdto.getPassword());
            values.put("name", sdto.getName());
            values.put("height", sdto.getHeight());
            values.put("weight", sdto.getWeight());
            values.put("sex", sdto.getSex());
            database.insert("user", null, values);
            Log.i("db1", "Success");
            select();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //Read the user data
    public SqliteDto read(String id) {
        SqliteDto sdto = null;
        try {
            database = helper.getWritableDatabase();
            Cursor c = database.rawQuery("select * from user where id=" + "'" + id + "'", null);
            c.moveToNext();
            sdto = new SqliteDto(c.getString(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getString(5));
            select();
            Log.i("db1", "Success");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return sdto;
    }
    //update the user information, 이름과 비밀번호 수정
    public boolean updateNamePassword(String userId, String name, String password) {
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("password", password);
            database.update("user", values, "id=?", new String[]{userId});
            select();
            Log.i("db1", "Success update");
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateHeightWeight(int ht, int wt) {
        try{
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("height", ht);
            values.put("weight", wt);
            database.update("user", values, "", null);
            select();
            Log.i("db1", "Success update");
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // Get the id values //이 부분도 써야될지 잘 모르겟다.
    public String getID() {
        database=helper.getWritableDatabase();
        Cursor c=database.rawQuery("select id from user",null);
        c.moveToNext();
        String gid=c.getString(0);
        select();
        return gid;
    }

    // Get the login user's height
    public int getcurHeight(String cid) {
        database=helper.getWritableDatabase();
        Cursor c=database.rawQuery("select height from user where id="+"'"+cid+"'",null );
        c.moveToNext();
        int cheight=c.getInt(0);
        select();
        return cheight;
    }
    //Get the login user's weight
    public int getcurWeight(String cid) {
        database=helper.getWritableDatabase();
        Cursor c=database.rawQuery("select weight from user where id="+"'"+cid+"'",null );
        c.moveToNext();
        int cweight=c.getInt(0);
        select();
        return cweight;
    }
    //Get the login user's sex
    public String getcurSex(String cid) {
        database=helper.getWritableDatabase();
        Cursor c=database.rawQuery("select sex from user where id="+"'"+cid+"'",null );
        c.moveToNext();
        String csex=c.getString(0);
        select();
        return csex;
    }

}
