package com.example.tutorscape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	1;
    private	static final String	DATABASE_NAME = "contact";
    private	static final String TABLE_CONTACTS = "contacts";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "contactname";
    private static final String COLUMN_NO = "phno";

    private static final String COLUMN_Title = "title";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_Phone = "phone";
    private static final String COLUMN_TRIAL = "trial";
    private static final String COLUMN_WEEKLYRATE = "weeklyRate";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_CONTACTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT UNIQUE," + COLUMN_NO + " INTEGER," + COLUMN_Title + " TEXT,"+ COLUMN_Phone + " TEXT," + COLUMN_IMAGE + " TEXT,"+ COLUMN_TRIAL + " TEXT," + COLUMN_WEEKLYRATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public ArrayList<Contacts> listContacts(){
        String sql = "select * from " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contacts> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phno = cursor.getString(2);
                String title = cursor.getString(3);
                String phone = cursor.getString(4);
                String image = cursor.getString(5);
                String trial = cursor.getString(6);
                String weeklyRate = cursor.getString(7);

                storeContacts.add(new Contacts(id, name, phno,title,image,phone,trial,weeklyRate));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void addContacts(Contacts contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contacts.getName());
        values.put(COLUMN_NO, contacts.getPhno());
        values.put(COLUMN_Title, contacts.getTitle());
        values.put(COLUMN_Phone, contacts.getPhone());
        values.put(COLUMN_IMAGE, contacts.getImage());
        values.put(COLUMN_TRIAL, contacts.getTrial());
        values.put(COLUMN_WEEKLYRATE, contacts.getWeeklyRate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
    }

    public void updateContacts(Contacts contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contacts.getName());
        values.put(COLUMN_NO, contacts.getPhno());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CONTACTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(contacts.getId())});
    }

    public Contacts findContacts(String name){
        String query = "Select * FROM "	+ TABLE_CONTACTS + " WHERE " + COLUMN_NAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Contacts contacts = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String contactsName = cursor.getString(1);
            String contactsNo = cursor.getString(2);
      //      contacts = new Contacts(id, contactsName, contactsNo);
        }
        cursor.close();
        return contacts;
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}