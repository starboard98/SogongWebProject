package com.module.gomodules.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

@Entity(name="RESERVATION")
public class ReservationVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private int covers;//식사 인원수
    private String name;
    private String start_time;
    private String date;
    private int table_number;

    public void setVal_id(String val) {
        this.id = val;
    }
    public void setVal_covers(int val) {
        this.covers = val;
    }
    public void setVal_table_number(int val) {
        this.table_number = val;
    }
    public void setVal_name(String val) {
        this.name = val;
    }
    public void setVal_date(String val) {
        this.date = val;
    }
    public void setVal_start_time(String val) {
        this.start_time = val;
    }

    public String getVal_id() {
        return this.id;
    }
    public int getVal_covers() {
        return this.covers;
    }
    public String getVal_start_time() {
        return this.start_time;
    }
    public String getVal_date() {
        return this.date;
    }
    public String getVal_name() {
        return this.name;
    }
    public int getVal_table_number() {
        return this.table_number;
    }

}