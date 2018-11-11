package com.example.linhongyu.smarter;

import java.util.Date;

/**
 * Created by linhongyu on 24/4/18.
 */

public class Resinfo {

    private int resid;
    private String firstname;
    private String surname;
    private Date dob;
    private String address;
    private String email;
    private String postcode;
    private long moblie;
    private int residentno;
    private String provider;

    public Resinfo() {
    }

    public Resinfo(Integer resid) {
        this.resid = resid;
    }

    public Resinfo(Integer resid, String firstname, String surname, Date dob, String address, String postcode, String email, long moblie, int residentno, String provider) {
        this.resid = resid;
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        this.moblie = moblie;
        this.residentno = residentno;
        this.provider = provider;
    }

    public Integer getResid() {
        return resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMoblie() {
        return moblie;
    }

    public void setMoblie(long moblie) {
        this.moblie = moblie;
    }

    public int getResidentno() {
        return residentno;
    }

    public void setResidentno(int residentno) {
        this.residentno = residentno;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
