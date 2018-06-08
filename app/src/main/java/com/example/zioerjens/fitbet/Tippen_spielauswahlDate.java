package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Tippen_spielauswahlDate extends Fragment {

    private View activity;
    public Button pickDateBtn;
    private Calendar selectedDate;
    private DatePicker datePicker;
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tippen__spielauswahl_date,container,false);
        return rootView;
    }

    public void showDialogOnEditTextClick() {
        pickDateBtn = (Button) activity.findViewById(R.id.selectDateBtn);

        pickDateBtn.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        DialogFragment newFragmentDate = new DatePickerFragment();
                        newFragmentDate.show(myContext.getFragmentManager(), "datePicker");

/*
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity.getContext());
                        View mView = getLayoutInflater().inflate(R.layout.date_picker,null);
                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        datePicker = dialog.findViewById(R.id.datePicker);
                        /*
                        datePicker.setOnDateChangedListener(
                                new DatePicker.OnDateChangedListener() {
                                    @Override
                                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        year = datePicker.getYear();
                                        month = datePicker.getMonth();
                                        day = datePicker.getDayOfMonth();
                                    }
                                }
                        );*/
                    }
                }
        );
    }

    @Override
    public void onStart() {

        super.onStart();
        activity = this.getView();
        selectedDate  = Calendar.getInstance();
        showDialogOnEditTextClick();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.e("date",year + "-" + month + "-" + day);
        }
    }

    public void loadMatches(){
        //TODO spiele für das besimmte Datum einfügen.
    }
}
