package fr.ensisa.rados.cafetariagestion.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fr.ensisa.rados.cafetariagestion.database.ProviderDao;
@Entity(tableName = "providers")
public class Provider {
    @PrimaryKey(autoGenerate = true)
    private long pid;
    private String name;
    private String phoneNumber;
    private int orderTime;  //Si on commande un produit mtn combien de temps il va faloir

    public Provider()
    {
        this.pid=0;
    }

    public Provider(String name, String phoneNumber, int orderTime) {
        this();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orderTime = orderTime;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }
}
