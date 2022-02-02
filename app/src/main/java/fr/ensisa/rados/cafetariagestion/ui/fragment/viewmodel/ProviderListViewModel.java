package fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.CafetDao;
import fr.ensisa.rados.cafetariagestion.database.ProviderDao;
import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Provider;

public class ProviderListViewModel extends ViewModel {

    private ProviderDao providerDao;
    public MediatorLiveData<List<Provider>> providers;

    public LiveData<List<Provider>> getProviders(){return providers;}

    public void setProviderDao(ProviderDao providerDao) {
        this.providerDao = providerDao;
        providers = new MediatorLiveData<>();
        providers.addSource(providerDao.getALll(),providers::setValue);
    }

    public void deleteProvider(Provider provider){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                providerDao.delete(provider);
            }
        });

    }
}