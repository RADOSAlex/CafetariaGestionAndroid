package fr.ensisa.rados.cafetariagestion.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.model.Product;
import fr.ensisa.rados.cafetariagestion.model.Provider;

@Dao
public interface ProviderDao {


    @Query("SELECT * FROM providers")
    LiveData<List<Provider>> getALll();

    @Query("SELECT * FROM providers WHERE pid = :id")
    LiveData<Provider> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Provider p);

    @Delete
    void delete(Provider p);

    //Peut faire un getByName pour trier .
}

