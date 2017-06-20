package com.example.manishchawley.computehelper.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.manishchawley.computehelper.render.TripDetailsActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by manishchawley on 12/07/17.
 */

public class Dialogs {

    public static void getDatePickerDialog(final Context context, final TextView editText){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year - Constants.ANDROID_YEAR_ADJUSTMENT, month, dayOfMonth);
                        editText.setText(DateUtils.getRelativeDateTimeString(context, calendar.getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
