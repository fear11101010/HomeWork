package com.example.homework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arafat Hossain on 1/18/2015.
 */
public class BirthDatePicker extends DialogFragment implements DialogInterface.OnClickListener{

    DatePicker dp;
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which){
            case DialogInterface.BUTTON_POSITIVE:
                int monthOfYear=dp.getMonth()+1;
                String date = dp.getDayOfMonth()+"/"+(monthOfYear<10?"0"+monthOfYear:monthOfYear)+"/"+dp.getYear();
                ods.setDate(date);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;

        }
    }
    public interface OnDateSet{
        public void setDate(String date);
    }
    OnDateSet ods;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View v = getActivity().getLayoutInflater().inflate(R.layout.datepickerlayout,null);
        dp = (DatePicker) v.findViewById(R.id.bdp);
        Bundle data = this.getArguments();
        if(data!=null) {
            int year = data.getInt("year");
            int monthOfYear = data.getInt("month");
            int dayOfMonth = data.getInt("day");
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
            try {
                Date d = sdf.parse(monthOfYear+"."+dayOfMonth+"."+year);
                dp.getCalendarView().setDate(d.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setView(v);
        ab.setTitle("Select your birthday");
        ab.setPositiveButton("Done",this);
        ab.setNegativeButton("Cancel",this);
        return ab.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ods = (OnDateSet) activity;
        }catch(ClassCastException e){

        }
    }

}

