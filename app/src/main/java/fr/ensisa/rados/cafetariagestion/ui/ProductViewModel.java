package fr.ensisa.rados.cafetariagestion.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.ProductDao;
import fr.ensisa.rados.cafetariagestion.model.Product;

public class ProductViewModel extends ViewModel {

    private LiveData<Product> product;
    private ProductDao productDao;
    private final MutableLiveData<Long> id = new MutableLiveData<>();

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
        this.product = Transformations.switchMap(id, v -> this.productDao.getById(v));
    }

    public void setId(long id) {
        this.id.postValue(id);
    }

    public LiveData<Product> getProduct(){
        return product;
    }

    public void saveProduct (Product p){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productDao.insert(p);
            }
        });
    }
}