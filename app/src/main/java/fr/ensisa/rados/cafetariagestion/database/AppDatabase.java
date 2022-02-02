package fr.ensisa.rados.cafetariagestion.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;
import fr.ensisa.rados.cafetariagestion.model.Provider;

@TypeConverters({Converters.class})
@Database(entities = {Product.class, Cafet.class, Provider.class}, version = 1, exportSchema = false)
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
    public abstract CafetDao cafetDao();
    public abstract ProviderDao providerDao();


    public void populateCafets(){
        cafetDao().insert(new Cafet("Werner", "0123456789", 0, "M.Hassenforder","Ensisa"));
    }
    public void populateProducts() {
        productDao().insert(new Product("Pizza", ProductType.FOOD, 10.0, 3.6, new Date("22/03/1999")));
    }

    private void populateProviders() {
        providerDao().insert(new Provider("Boulangerie Germain", "06080484", 3, "Rue de la Bastille", "alexandre.rados@uha.fr"));
    }
    public void populate() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                populateProducts();
                populateCafets();
                populateProviders();
            }
        });
    }


}
