package fr.ensisa.rados.cafetariagestion.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ensisa.rados.cafetariagestion.R;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;


public class DataBindingAdapters {

    private static SimpleDateFormat output = null;

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
    @BindingAdapter("android:background")
    public static void setBackground (View view, ProductType productType) {
        int colorId = R.color.color_no;
        if (productType != null) {
            switch (productType) {
                case DRINKS: colorId = R.color.color_drinks; break;
                case FOOD: colorId = R.color.color_food; break;
            }
        }
        int color = view.getResources().getColor(colorId, null);
        view.setBackgroundColor(color);
    }

    @BindingAdapter("android:text")
    public static void setText (TextView view, ProductType productType) {
        String text = null;
        if (productType != null) {
            switch (productType) {
                case DRINKS: text = "Drinks" ; break;
                case FOOD: ; text = "Food"; break;
            }
        }
        view.setText(text);
    }

    @BindingAdapter("android:image")
    public static void SetImage (ImageView view, Product product) {
        if (product == null) return;
        if (product.getProductType() != null) {
            switch (product.getProductType()) {

                case FOOD:
                    view.setImageResource(R.drawable.food_icon);
                    break;
                case DRINKS:
                    view.setImageResource(R.drawable.drink);
                    break;
            }
        }
    }


    static private SimpleDateFormat formatter = null;

    public static String format (Date date){
        if (date == null) return "no date";
        if (formatter == null){
            formatter = new SimpleDateFormat("dd MMMM yyyy");
        }
        return formatter.format(date);
    }

}
