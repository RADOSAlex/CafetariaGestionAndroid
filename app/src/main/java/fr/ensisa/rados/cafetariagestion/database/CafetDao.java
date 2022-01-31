package fr.ensisa.rados.cafetariagestion.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.Product;

@Dao
public interface CafetDao {
    @Query("SELECT * FROM cafets")
    LiveData<List<Cafet>> getALll();

    @Query("SELECT * FROM cafets WHERE cid = :id")
    LiveData<Cafet> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cafet c);

    @Delete
    void delete(Cafet c);

    //Peut faire un getByName pour trier .
}
