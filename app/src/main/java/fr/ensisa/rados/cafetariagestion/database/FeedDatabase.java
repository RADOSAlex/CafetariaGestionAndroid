package fr.ensisa.rados.cafetariagestion.database;

import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.CafetProductAssociation;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.ProductType;
import fr.ensisa.rados.cafetariagestion.model.Provider;

public class FeedDatabase {

    private long[] feedProducts() {
        long[] ids = new long[3];
        ProductDao dao = AppDatabase.getInstance().productDao();
        ids[0] =dao.insert(new Product("Pizza Salami", ProductType.FOOD, 5.0, 3.6, new GregorianCalendar(2022, 01, 29).getTime()));
        ids[1] =dao.insert(new Product("Pizza Chèvre", ProductType.FOOD, 5.0, 3.6, new GregorianCalendar(2022, 01, 29).getTime()));
        ids[2] =dao.insert(new Product("7up", ProductType.DRINKS, 10.0, 0.8, new GregorianCalendar(2022, 01, 29).getTime()));
        return ids;
    }

    private void feedCafet() {

        CafetDao dao = AppDatabase.getInstance().cafetDao();
        dao.insert(new Cafet("Werner", "0123456789", 0, "M.Hassenforder","Ensisa"));

    }

    public void feed(){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long[] pids = feedProducts();
                feedCafets(pids);
            }
        });
    }

    private long[] feedCafets(long[] ids) {
        long[] cids = new long[1];
        CafetDao dao = AppDatabase.getInstance().cafetDao();
        cids[0]=dao.insert(new Cafet("Werner", "0123456789", 0, "M.Hassenforder","Ensisa"));
        dao.addCafetProduct(new CafetProductAssociation(cids[0], ids[0]));
        dao.addCafetProduct(new CafetProductAssociation(cids[0], ids[1]));
        dao.addCafetProduct(new CafetProductAssociation(cids[0], ids[2]));
        return cids;
    }

    private void feedProviders(){
        ProviderDao dao = AppDatabase.getInstance().providerDao();
        dao.insert(new Provider("Boulangerie Germain", "06080484", 3, "Rue de la Bastille", "alexandre.rados@uha.fr"));
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
                feedCafet();
            }
        });
    }

    public void feedP2() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                feedProviders();
            }
        });
    }



}