package com.example.edehaari;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String id;
    private String name;
    private String phoneNo;
    private String password;
    private boolean isWorker;

    public UserModel() {
    }

    public UserModel(String id, String name, String phoneNo, String password, boolean isWorker) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.password = password;
        this.isWorker = isWorker;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWorker() {
        return isWorker;
    }

    public void setWorker(boolean worker) {
        isWorker = worker;
    }
}
