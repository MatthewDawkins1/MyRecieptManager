package com.ict311task2dawkins.android.myrecieptmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DialogDateFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.ict311task2dawkins.android.myreciptmanager.date";
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    public static DialogDateFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DialogDateFragment fragment = new DialogDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(calendar.YEAR);
        int mounth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);

        mDatePicker = v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year,mounth,day,null);

        return new AlertDialog.Builder(getActivity()).setTitle("Recipt Date").setView(v).setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
            int year1 = mDatePicker.getYear();
            int month = mDatePicker.getMonth();
            int day1 = mDatePicker.getDayOfMonth();
            Date date = new GregorianCalendar(year1,month,day1).getTime();
            sendResult(Activity.RESULT_OK, date);
        }).create();
    }

    private void sendResult(int resultCode, Date date){
        if (getTargetFragment().equals(null)){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }


}
