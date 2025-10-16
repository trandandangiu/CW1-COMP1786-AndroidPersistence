package com.example.cw1_androidpersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 2;

    // Table name
    private static final String TABLE_CONTACTS = "contacts";

    // Column names
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";
    private static final String COL_EMAIL = "email";
    private static final String COL_AVATAR = "avatar"; // URI string

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CONTACTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_AVATAR + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // 🟢 Add new contact
    public boolean addContact(String name, String phone, String email, String avatarUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        cv.put(COL_EMAIL, email);
        cv.put(COL_AVATAR, avatarUri);

        long result = db.insert(TABLE_CONTACTS, null, cv);
        db.close();
        return result != -1;
    }

    // 🟡 Get all contacts
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        if (cursor.moveToFirst()) {
            do {
                Contact c = new Contact();
                c.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                c.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                c.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)));
                c.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                c.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COL_AVATAR)));
                contacts.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    // 🔵 Delete contact
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 🟣 Update contact (optional)
    public boolean updateContact(int id, String name, String phone, String email, String avatarUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        cv.put(COL_EMAIL, email);
        cv.put(COL_AVATAR, avatarUri);
        int result = db.update(TABLE_CONTACTS, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
