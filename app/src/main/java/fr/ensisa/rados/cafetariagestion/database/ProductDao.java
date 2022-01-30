package fr.ensisa.rados.cafetariagestion.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.ensisa.rados.cafetariagestion.model.Product;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getALll();

    @Query("SELECT * FROM products WHERE pid = :id")
    LiveData<Product> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product p);

    @Delete
    void delete(Product p);

    //Peut faire un getByName pour trier .
}
