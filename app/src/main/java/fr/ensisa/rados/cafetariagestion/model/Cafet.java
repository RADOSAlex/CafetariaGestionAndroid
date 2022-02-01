package fr.ensisa.rados.cafetariagestion.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cafets")
public class Cafet {
    @PrimaryKey(autoGenerate = true)
    private long cid;
    private String name;
    private String telephone;
    private int productNumber;

    public Cafet() {
        this.cid = 0;
    }

    @Ignore
    public Cafet(String name, String telephone, int productNumber) {
        this();
        this.name = name;
        this.telephone = telephone;
        this.productNumber=productNumber;
    }


    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }
}
