package fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.ProductDao;
import fr.ensisa.rados.cafetariagestion.database.ProviderDao;
import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.Provider;

public class ProviderViewModel extends ViewModel {
    private LiveData<Provider> provider;
    private ProviderDao providerDao;
    private final MutableLiveData<Long> id = new MutableLiveData<>();

    public void setProviderDao(ProviderDao providerDao) {
        this.providerDao = providerDao;
        this.provider = Transformations.switchMap(id, v -> this.providerDao.getById(v));
    }

    public void setId(long id) {
        this.id.postValue(id);
    }

    public LiveData<Provider> getProvider(){
        return provider;
    }


    public void createProvider() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Provider provider = new Provider ();
                long id = providerDao.insert(provider);
                setId(id);
            }
        });

    }
    public void saveProvider (Provider p){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                providerDao.insert(p);
            }
        });
    }
}