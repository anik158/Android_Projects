package com.ahsan.mycontacts;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContactsDatabase";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_HOME = "phone_home";
    private static final String KEY_PHONE_OFFICE = "phone_office";
    private static final String KEY_PHOTO = "photo";

    public DatabaseHelper(Activity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PHONE_HOME + " TEXT,"
                + KEY_PHONE_OFFICE + " TEXT," + KEY_PHOTO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

    public long saveContact(String name, String email, String phoneHome, String phoneOffice, String photo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE_HOME, phoneHome);
        values.put(KEY_PHONE_OFFICE, phoneOffice);
        values.put(KEY_PHOTO, photo);

        long id = db.insert(TABLE_CONTACTS, null, values);
        db.close();
        return id;
    }

    public boolean deleteContact(String contactName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_CONTACTS, KEY_NAME + "=?", new String[]{contactName});
        db.close();
        return rowsAffected > 0;
    }


    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                int nameIndex = cursor.getColumnIndex(KEY_NAME);
                String name = "";
                int emailIndex = cursor.getColumnIndex(KEY_EMAIL);
                String email = "";
                int phoneHomeIndex = cursor.getColumnIndex(KEY_PHONE_HOME);
                String phoneHome = "";
                int phoneOfficeIndex = cursor.getColumnIndex(KEY_PHONE_OFFICE);
                String phoneOffice = "";
                int photoIndex = cursor.getColumnIndex(KEY_PHOTO);
                String photo = "";

                if(nameIndex != -1){
                    name = cursor.getString(nameIndex);
                }

                if(emailIndex != -1){
                    email = cursor.getString(emailIndex);
                }

                if(phoneHomeIndex != -1){
                    phoneHome = cursor.getString(phoneHomeIndex);
                }

                if(phoneOfficeIndex != -1){
                    phoneOffice = cursor.getString(phoneOfficeIndex);
                }

                if(photoIndex != -1){
                    photo = cursor.getString(photoIndex);
                }


                Contact contact = new Contact(name, email, phoneHome, phoneOffice, photo);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return contactList;
    }

    public boolean updateContact(String originalName, String newName, String email, String phoneHome, String phoneOffice, String photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, newName);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE_HOME, phoneHome);
        values.put(KEY_PHONE_OFFICE, phoneOffice);
        values.put(KEY_PHOTO, photo);

        int rowsAffected = db.update(TABLE_CONTACTS, values, KEY_NAME + "=?", new String[]{originalName});
        db.close();
        return rowsAffected > 0;
    }

    public Contact getContact(String contactName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_NAME, KEY_EMAIL, KEY_PHONE_HOME, KEY_PHONE_OFFICE, KEY_PHOTO},
                KEY_NAME + "=?", new String[]{contactName}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = null;
        if (cursor.getCount() > 0) {
            contact = new Contact(
                    cursor.getString(0), // Name
                    cursor.getString(1), // Email
                    cursor.getString(2), // Phone Home
                    cursor.getString(3), // Phone Office
                    cursor.getString(4)  // Photo
            );
        }
        cursor.close();
        return contact;
    }



}