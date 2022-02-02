package fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.CafetDao;
import fr.ensisa.rados.cafetariagestion.database.ProductDao;
import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.FullCafet;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.database.Transformations;

public class CafetViewModel extends ViewModel {
    private LiveData<Cafet> cafet;
    private LiveData<FullCafet> fullCafet;
    private CafetDao cafetDao;
    private ProductDao productDao;
    private final MutableLiveData<Long> id = new MutableLiveData<>();
    private MediatorLiveData<List<Product>> products ;

    public void setCafetDao(CafetDao cafetDao) {
        this.cafetDao = cafetDao;
        this.cafet = Transformations.switchMap(id, v -> this.cafetDao.getByCid(v));
        this.fullCafet= Transformations.switchMap(id, v -> this.cafetDao.getById(v));
        this.products=Transformations.map(fullCafet, fullCafet-> new ArrayList<>(fullCafet.products));
    }

    public void setId(long id) {
        this.id.postValue(id);
    }

    public LiveData<Cafet> getCafet() {
        return cafet;
    }

    public void saveCafet(Cafet c) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cafetDao.insert(c);
            }
        });
    }

    public void createCafet() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Cafet cafet = new Cafet();
                long id = cafetDao.insert(cafet);
                setId(id);
            }
        });
    }


    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public LiveData<FullCafet> getFullCafet() {
        return fullCafet;
    }


}