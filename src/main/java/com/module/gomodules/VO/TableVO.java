package com.module.gomodules.VO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="TABLE1")
public class TableVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oid;
    private int number;

    public void setVal_oid(long val) {
        this.oid = val;
    }
    public void setVal_rid(int val) {
        this.number = val;
    }
    public long getVal_oid() {
        return this.oid;
    }
    public int getVal_number() {
        return this.number;
    }
}
