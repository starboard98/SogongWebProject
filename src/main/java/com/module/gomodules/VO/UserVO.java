package com.module.gomodules.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="USER")
public class UserVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;
    private String id;
    private String name;
    private String password;
    private String phonenumber;

    public void setVal_id(String val) { this.id = val; }
    public void setVal_name(String val) {
            this.name = val;
        }
    public void setVal_password(String val) {
            this.password = val;
        }
    public void setVal_phonenumber(String val) {
            this.phonenumber = val;
        }
    public void setVal_oid(int val) {
            this.oid = val;
        }

    public String getVal_id() {
        return this.id;
    }
    public String getVal_name() {
        return this.name;
    }
    public String getVal_password() {
        return this.password;
    }
    public String getVal_phonenumber() {
        return this.phonenumber;
    }
    public int getVal_oid() {
        return this.oid;
    }
}
