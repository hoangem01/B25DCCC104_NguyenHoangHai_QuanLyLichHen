package com.example.datlich.sqlite;

import com.example.datlich.models.Appointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng users
        String createTableUsers = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL)";
        db.execSQL(createTableUsers);

        // Bảng appointments
        String createTableAppointments = "CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "location TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "time TEXT NOT NULL, " +
                "reminder INTEGER NOT NULL)";
        db.execSQL(createTableAppointments);

        // Chèn tài khoản mẫu
        ContentValues userValues = new ContentValues();
        userValues.put("username", "admin");
        userValues.put("password", "123456");
        db.insert("users", null, userValues);

        // Chèn 3 lịch hẹn mẫu
        insertDummyAppointment(db, "Họp nhóm đồ án", "Phòng B203", "2026-10-25", "09:00", 1);
        insertDummyAppointment(db, "Khám răng", "Nha khoa Minh Tâm", "2026-10-26", "14:30", 1);
        insertDummyAppointment(db, "Đi cà phê với bạn", "Highland Coffee", "2026-10-27", "19:00", 0);
    }

    private void insertDummyAppointment(SQLiteDatabase db, String title, String location, String date, String time, int reminder) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("location", location);
        values.put("date", date);
        values.put("time", time);
        values.put("reminder", reminder);
        db.insert("appointments", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS appointments");
        onCreate(db);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public long insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        return db.insert("users", null, values);
    }

    public long insertAppointment(String title, String location, String date, String time, int reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("location", location);
        values.put("date", date);
        values.put("time", time);
        values.put("reminder", reminder);
        return db.insert("appointments", null, values);
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM appointments ORDER BY date ASC, time ASC", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Appointment(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Appointment getAppointmentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM appointments WHERE id = ?", new String[]{String.valueOf(id)});
        Appointment appointment = null;
        if (cursor.moveToFirst()) {
            appointment = new Appointment(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
        }
        cursor.close();
        return appointment;
    }

    public void updateReminder(int id, boolean on) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reminder", on ? 1 : 0);
        db.update("appointments", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAppointment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("appointments", "id = ?", new String[]{String.valueOf(id)});
    }

    public int countAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM appointments", null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int countReminders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM appointments WHERE reminder = 1", null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }
}
