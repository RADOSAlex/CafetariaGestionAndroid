package fr.ensisa.rados.cafetariagestion.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;

public class FeedDatabase {

    private void feedProducts() {
        ProductDao dao = AppDatabase.getInstance().productDao();
        dao.insert(new Product("Pizza Salami", ProductType.FOOD, 5.0, 3.6));
        dao.insert(new Product("Pizza Chèvre", ProductType.FOOD, 5.0, 3.6));
        dao.insert(new Product("7up", ProductType.DRINKS, 10.0, 0.8));
    }

    public void feed() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                feedProducts();
            }
        });
    }
}