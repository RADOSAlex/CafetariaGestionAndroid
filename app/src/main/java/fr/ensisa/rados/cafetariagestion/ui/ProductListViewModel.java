package fr.ensisa.rados.cafetariagestion.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

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


}