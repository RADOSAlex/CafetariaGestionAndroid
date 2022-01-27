package fr.ensisa.rados.cafetariagestion.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {


    @PrimaryKey(autoGenerate = true)
    private long pid;
    private String name;
    private ProductType productType;
    private Double quantity;
    private Double price;
    //Date d'expiration ?

    public Product() {
        this.pid = 0;
    }

    @Ignore
    public Product(String name, ProductType productType, Double quantity, Double price) {
        this();
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
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
        name = name;
    }

    public ProductType getProductType() {
        return productType;
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
        return "@" + pid + " " + productType + ":" + name + '.' + price + '€';
    }
}