package com.module.gomodules.VO;

public class modefiedReservation {
    public int oid;
    public int covers;
    public String start_time;
    public int rank;
    public int table_number;
    public void setVal_oid(int oid) {
        this.oid = oid;
        }
        public void setVal_covers(int covers){
        this.covers=covers;
        }
        public void setVal_start_time(String start_time){
        this.start_time=start_time;
        }
        public void setVal_rank(int rank){
        this.rank=rank;
        }
        public void setVal_table_number(int table_number){
        this.table_number=table_number;
    }
}

