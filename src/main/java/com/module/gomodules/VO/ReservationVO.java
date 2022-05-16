package com.module.gomodules.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="RESERVATION")
public class ReservationVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;
    private int uid;
    private int covers;//식사 인원수
    private String name;
    private String start_time;
    private String date;
    private int table_number;
    private int rank;
    private int isdeleted;

    public void setVal_oid(int val) {
        this.oid = val;
    }
    public void setVal_uid(int val) {
        this.uid = val;
    }
    public void setVal_covers(int val) {
        this.covers = val;
    }
    public void setVal_start_time(String val) {
        this.start_time = val;
    }
    public void setVal_table_number(int val) {
        this.table_number = val;
    }
    public void setVal_rank (int val) {
        this.rank = val;
    }
    public void setVal_isdeleted (int val) {
        this.isdeleted = val;
    }

    public int getVal_oid() {
        return this.oid;
    }
    public int getVal_uid() {
        return this.uid;
    }
    public int getVal_covers() {
        return this.covers;
    }
    public String getVal_start_time() {
        return this.start_time;
    }
    public int getVal_table_number() {
        return this.table_number;
    }
    public int getVal_rank() {
        return this.rank;
    }
    public int getVal_isdeleted() {
        return this.isdeleted;
    }
}