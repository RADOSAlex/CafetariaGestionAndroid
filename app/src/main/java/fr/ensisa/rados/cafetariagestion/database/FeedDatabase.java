package fr.ensisa.rados.cafetariagestion.database;

import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;

public class FeedDatabase {

    private void feedProducts() {
        ProductDao dao = AppDatabase.getInstance().productDao();
        dao.insert(new Product("Pizza Salami", ProductType.FOOD, 5.0, 3.6, new GregorianCalendar(2022, 01, 29).getTime()));
        dao.insert(new Product("Pizza Chèvre", ProductType.FOOD, 5.0, 3.6, new GregorianCalendar(2022, 01, 29).getTime()));
        dao.insert(new Product("7up", ProductType.DRINKS, 10.0, 0.8, new GregorianCalendar(2022, 01, 29).getTime()));
    }

    private void feedCafets() {
        CafetDao dao = AppDatabase.getInstance().cafetDao();
        dao.insert(new Cafet("Werner", "15184", 0));

    }

    public void feedP() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                feedProducts();
            }
        });
    }

    public void feedC() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                feedCafets();
            }
        });
    }
}