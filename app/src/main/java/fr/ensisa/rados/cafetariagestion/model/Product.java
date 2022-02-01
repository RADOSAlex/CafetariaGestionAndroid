package fr.ensisa.rados.cafetariagestion.model;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity(tableName = "products")
public class Product {


    @PrimaryKey(autoGenerate = true)
    private long pid;
    private String name;
    private ProductType productType;
    private Double quantity;
    private Double price;
    private Date expirationDate;
    private Bitmap image;
    //Date d'expiration ?

    public Product() {
        this.pid = 0;
        this.expirationDate= Calendar.getInstance().getTime();
    }

    @Ignore
    public Product(String name, ProductType productType, Double quantity, Double price, Date expirationDate) {
        this();
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
        this.expirationDate=expirationDate;

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

    public ProductType getProductType() {
        return productType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }




    @Override
    public String toString() {
        return "@" + pid + " " + productType + ":" + name + '.' + price + '€' + expirationDate ;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}