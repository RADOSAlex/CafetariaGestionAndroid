package fr.ensisa.rados.cafetariagestion.ui.fragment;

import androidx.fragment.app.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static public final String ARG_DATE = "date";
    private String requestKey;
    private Date initial;

    public interface OnChangedDate {
        void onDateChanged(Date date);
    }
    private OnChangedDate listener;

    public DatePickerFragment(){}

    public static DatePickerFragment newInstance(Date date){
        DatePickerFragment fragment=new DatePickerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DATE, date.getTime());
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year=0;
        int month=0;
        int dayOfMonth=0;
        if (getArguments() != null
        ) {
            long date = getArguments().getLong(ARG_DATE);
            Calendar calendar= GregorianCalendar.getInstance();
            calendar.setTime(new Date(date));
            calendar.get(Calendar.YEAR);
            calendar.get(Calendar.MONTH);
            calendar.get(Calendar.DAY_OF_MONTH);

        }
        DatePickerDialog dialog = new DatePickerDialog(getContext(), this, year, month, dayOfMonth);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            long date = getArguments().getLong(ARG_DATE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        Fragment target = getTargetFragment();
        if(target== null){
            throw new ClassCastException("DatePickerFragment should not be null");

        }else if(target instanceof  OnChangedDate){
            listener = (OnChangedDate) target;
        }
    }
    public void onDetatch(){
        super.onDetach();
        listener=null;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date date = calendar.getTime();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        listener.onDateChanged(date);
    }


}

