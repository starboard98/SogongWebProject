package com.module.gomodules.VO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class modifyingDateAndTime {
    public int oid;
    public int covers;
    public String start_time;
    public int rank;
    public int table_number;
    public String date;//yyyy-MM-dd
    public String time;//HH:MM

    public void setVal_oid(int oid){
        this.oid=oid;
    }
    public void setVal_covers(int covers){
        this.covers=covers;
    }
    public void setVal_start_time(String start_time){
        this.start_time=start_time;
        /// 날짜 형식 변환 코드부분 //////////////////////
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat out1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat out2 = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = in.parse(start_time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.date = out1.format(date);
        this.time = out2.format(date);
        /// 날짜 형식 변환 끝 //////////////////////////


    }
    public void setVal_table_number(int table_number){
        this.table_number=table_number;
    }
}
