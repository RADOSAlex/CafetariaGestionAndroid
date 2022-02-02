package fr.ensisa.rados.cafetariagestion.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(
        tableName = "CProdas",
        primaryKeys = { "cid", "pid" },
        indices = { @Index("cid"), @Index("pid") }
)
public class CafetProductAssociation {

    public long cid;
    public long pid;

    public CafetProductAssociation() {

    }

    @Ignore
    public CafetProductAssociation(long cid, long pid) {
        this.cid = cid;
        this.pid = pid;
    }
}
