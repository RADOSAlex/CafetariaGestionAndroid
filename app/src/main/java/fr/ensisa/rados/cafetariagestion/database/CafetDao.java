package fr.ensisa.rados.cafetariagestion.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.model.Cafet;
import fr.ensisa.rados.cafetariagestion.model.CafetProductAssociation;
import fr.ensisa.rados.cafetariagestion.model.FullCafet;
import fr.ensisa.rados.cafetariagestion.model.Product;

@Dao
public interface CafetDao {
    @Query("SELECT * FROM cafets")
    LiveData<List<Cafet>> getALll();

    @Query("SELECT * FROM cafets WHERE cid = :id")
    LiveData<Cafet> getByCid(long id);

    @Transaction
    @Query("SELECT * FROM cafets WHERE cid = :id")
    LiveData<FullCafet> getById(long id);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long  insert(Cafet c);

    @Delete
    void delete(Cafet c);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addCafetProduct(CafetProductAssociation product);

    @Delete
    void removeCafetProduct(CafetProductAssociation product);



}
