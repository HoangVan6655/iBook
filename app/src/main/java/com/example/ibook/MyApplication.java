package com.example.ibook;

import android.app.Application;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class MyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
    }

    //tạo phương thức conver timestamp to dd/MM/yyyy
    public static final String formatTimestamp(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);

        //format
        String date = DateFormat.format("dd/MM/yyyy",cal).toString();
        return date;
    }

}
