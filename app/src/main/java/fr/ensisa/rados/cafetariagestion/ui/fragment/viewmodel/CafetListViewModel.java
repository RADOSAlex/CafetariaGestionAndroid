package fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.CafetDao;
import fr.ensisa.rados.cafetariagestion.database.ProductDao;
import fr.ensisa.rados.cafetariagestion.model.Cafet;

public class CafetListViewModel extends ViewModel {

    private CafetDao cafetDao;
    public MediatorLiveData<List<Cafet>> cafets;

    public LiveData<List<Cafet>> getCafets(){return cafets;}

    public void setCafetDao(CafetDao cafetDao) {
        this.cafetDao = cafetDao;
        cafets = new MediatorLiveData<>();
        cafets.addSource(cafetDao.getALll(),cafets::setValue);
    }

    public void deleteCafet(Cafet cafet){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cafetDao.delete(cafet);
            }
        });

    }
}