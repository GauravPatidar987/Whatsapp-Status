package com.wh;
import android.database.sqlite.*;
import android.content.*;
import android.database.*;
import java.util.*;

public class SQLiteHelper extends SQLiteOpenHelper
{
	Context c;
	public SQLiteHelper(Context c)
	{
		super(c, Params.DB_NAME, null, Params.DB_VERSION);
		this.c = c;
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String create="CREATE TABLE " + Params.TABLE_NAME + "(" +
			Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			Params.USER_NAME + " TEXT," + Params.STATUS + " TEXT" + ")";
		db.execSQL(create);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		db.execSQL("DROP TABLE IF EXISTS " + Params.TABLE_NAME);  
        onCreate(db);  
	}
	public void addStatus(Status status)
	{  
        SQLiteDatabase db = this.getWritableDatabase();  

        ContentValues values = new ContentValues();  
        values.put(Params.USER_NAME, status.getUsername());
        values.put(Params.STATUS, status.getStatus()); 
        db.insert(Params.TABLE_NAME, null, values);       
        db.close(); 
    }  
	Status getStatus(int id)
	{  
        SQLiteDatabase db = this.getReadableDatabase();  

        Cursor cursor = db.query(Params.TABLE_NAME, new String[] { Params.KEY_ID
									 ,  
									 Params.USER_NAME, Params.STATUS }, Params.KEY_ID + "=?",  
								 new String[] { String.valueOf(id) }, null, null, null, null);  
        if (cursor != null)  
            cursor.moveToFirst();  

        Status Status = new Status(Integer.parseInt(cursor.getString(0)),  
								   cursor.getString(1), cursor.getString(2));  

        return Status;  
    }  

	public List<Status> getAllStatus()
	{  
        List<Status> statusList = new ArrayList<Status>();  
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + Params.TABLE_NAME;  

        SQLiteDatabase db = this.getWritableDatabase();  
        Cursor cursor = db.rawQuery(selectQuery, null);  
        if (cursor.moveToFirst())
		{  
            do {  
                Status status = new Status();  
                status.setId(Integer.parseInt(cursor.getString(0)));  
                status.setUsername(cursor.getString(1));  
                status.setStatus(cursor.getString(2));  

                statusList.add(status);  
            } while (cursor.moveToNext());  
        }  

        // return Status list  
        return statusList;  
    }  
	public int updateStatus(Status status)
	{  
        SQLiteDatabase db = this.getWritableDatabase();  

        ContentValues values = new ContentValues();  
        values.put(Params.USER_NAME, status.getUsername());  
        values.put(Params.STATUS, status.getStatus());  

        // updating row  
        return db.update(Params.TABLE_NAME, values, Params 
						 .KEY_ID + " = ?",  
						 new String[] { String.valueOf(status.getId()) });  
    }  
	public void deleteStatus(Status status) {  
        SQLiteDatabase db = this.getWritableDatabase();  
        db.delete(Params.TABLE_NAME,Params. KEY_ID + " = ?",  
				  new String[] { String.valueOf(status.getId()) });  
        db.close();  
    }  
	public int getStatusCount() {  
        String countQuery = "SELECT  * FROM " + Params.TABLE_NAME;  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery(countQuery, null);
		int count=cursor.getCount();
        cursor.close();  
        return count;  
    }  
}
