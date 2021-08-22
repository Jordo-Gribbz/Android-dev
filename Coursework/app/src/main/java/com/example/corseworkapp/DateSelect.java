package com.example.corseworkapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateSelect extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar Cal = Calendar.getInstance();
        int Day = Cal.get(Calendar.DAY_OF_MONTH);
        int Month = Cal.get(Calendar.MONTH);
        int Year = Cal.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), Year, Month, Day);
    }
}
