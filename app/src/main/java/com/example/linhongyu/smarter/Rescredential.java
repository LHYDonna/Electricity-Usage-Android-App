package com.example.linhongyu.smarter;

import java.util.Date;

/**
 * Created by linhongyu on 24/4/18.
 */

public class Rescredential {
    private String username;
    private String passwordhash;
    private Date registrationdate;
    private Resinfo resid;

    public Rescredential() {

    }

    public Rescredential(String username) {

        this.username = username;
    }

    public Rescredential(String username, String passwordhash, Date registrationdate) {
        this.username = username;
        this.passwordhash = passwordhash;
        this.registrationdate = registrationdate;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPasswordhash() {

        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {

        this.passwordhash = passwordhash;
    }

    public Date getRegistrationdate() {

        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    public Resinfo getResid() {

        return resid;
    }

    public void setResid(Resinfo resid) {
        this.resid = resid;
    }
}
