package fr.ensisa.rados.cafetariagestion.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;
@TypeConverters({Converters.class})
@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    static private AppDatabase instance = null;

    static public void createInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "products.db").build();
        }
    }

    public static AppDatabase getInstance() {
        return instance;
    }

    public abstract ProductDao productDao();


    public void populateProducts() {
        productDao().insert(new Product("Pizza", ProductType.FOOD, 10.0, 3.6, new Date("22/03/1999")));
    }

    public void populate() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                populateProducts();
            }
        });
    }
}
