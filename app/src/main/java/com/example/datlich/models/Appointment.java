package com.example.datlich.models;

public class Appointment {
    private int id;
    private String title;
    private String location;
    private String date; // yyyy-MM-dd
    private String time; // HH:mm
    private int reminder; // 1 hoặc 0

    public Appointment(int id, String title, String location, String date, String time, int reminder) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.reminder = reminder;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getReminder() { return reminder; }
    public void setReminder(int reminder) { this.reminder = reminder; }
}