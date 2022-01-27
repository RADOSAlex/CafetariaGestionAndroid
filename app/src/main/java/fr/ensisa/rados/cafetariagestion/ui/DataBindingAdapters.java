package fr.ensisa.rados.cafetariagestion.ui;

import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;


public class DataBindingAdapters {
    @BindingAdapter("android:text")
    public static void setText(TextView view, double currentValue) {
        boolean todo = false;
        if (! todo && view.getText() == null) todo = true;
        if (! todo && view.getText().toString().isEmpty()) todo = true;
        if (! todo) {
            try {
                int inView = Integer.parseInt(view.getText().toString());
                if (inView != currentValue) todo = true;
            } catch (NumberFormatException e) {
                todo = true;
            }
        }
        if (todo) view.setText(Double.toString(currentValue));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(EditText view) {
        if (view.getText() == null) return 0;
        if (view.getText().toString().isEmpty()) return 0;
        try {
            double inView = Double.parseDouble(view.getText().toString());
            return inView;
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
