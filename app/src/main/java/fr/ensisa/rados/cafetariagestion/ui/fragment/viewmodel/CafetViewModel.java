package fr.ensisa.rados.cafetariagestion.ui.fragment.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.ensisa.rados.cafetariagestion.database.CafetDao;
import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Product;

public class CafetViewModel extends ViewModel {
    private LiveData<Cafet> cafet;
    private CafetDao cafetDao;
    private final MutableLiveData<Long> id = new MutableLiveData<>();

    public void setCafetDao(CafetDao cafetDao) {
        this.cafetDao = cafetDao;
        this.cafet = Transformations.switchMap(id, v -> this.cafetDao.getById(v));
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
}