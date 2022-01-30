package fr.ensisa.rados.cafetariagestion.database;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.Locale;

import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;

public class Converters {
    private static Product product;

    //Fonctions pour convertir un type de produit en string et inversement.
    @TypeConverter
    public static ProductType toProductType(String text){
        return text == null ? null : ProductType.valueOf(text.toUpperCase(Locale.ROOT));
    }

    @TypeConverter
    public static String fromProductType(ProductType productType){
        return productType == null ? null : productType.toString().toLowerCase();
    }

    @TypeConverter
    public static Double toDouble(String text){
        return text ==null? null: Double.parseDouble(text);
    }

    @TypeConverter
    public static String fromDouble(Double nb){
        return nb == null? null: String .valueOf(nb);
    }

    @TypeConverter
    public static Date long2Date (long time) {
        if (time == -1) return null;
        return new Date (time);
    }

    @TypeConverter
    public static long date2Long (Date date) {
        if (date == null) return -1;
        return date.getTime();
    }

}
