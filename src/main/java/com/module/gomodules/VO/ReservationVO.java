package com.module.gomodules.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="RESERVATION")
public class ReservationVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;    //기본키
    private int uid;    //유저의 기본키
    private String name;    //유저 이름
    private int covers; //식사 인원수
    private String date;        //예약 날짜
    private String start_time;  //예약 시간
    private int table_number;   //테이블 번호

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

    public void setVal_name(String val){this.name = val;}
    public void setVal_date(String val){this.date = val;}

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
    public String getVal_name(){return this.name;}
    public String getVal_date(){return this.date;}
}