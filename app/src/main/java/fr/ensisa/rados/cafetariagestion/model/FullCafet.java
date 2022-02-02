package fr.ensisa.rados.cafetariagestion.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class FullCafet {

    @Embedded
    public Cafet cafet;
    @Relation(parentColumn = "cid", entityColumn = "pid")
    public Product product;
    @Relation(
            parentColumn = "cid",
            entityColumn = "pid",
            associateBy = @Junction(CafetProductAssociation.class)
    )
    public List<Product> products;

}

