package fr.ensisa.rados.cafetariagestion.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.ProductDao;
import fr.ensisa.rados.cafetariagestion.model.Product;

public class ProductListViewModel extends ViewModel {

    private ProductDao productDao;
    public MediatorLiveData<List<Product>> products;


    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
        products = new MediatorLiveData<>();
        products.addSource(productDao.getALll(),products::setValue);
    }


    public void deleteProduct(Product product) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productDao.delete(product);
            }
        });

    }
}