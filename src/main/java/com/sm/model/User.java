package com.sm.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private int role;
    private int state;
    private int p1_npy_state;
    private int p2_npy_state;

    public User() {
    }

    public User(int id, String name, String password, String email, int role, int p1_npy_state, int p2_npy_state) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.state = state;
        this.p1_npy_state = p1_npy_state;
        this.p2_npy_state = p2_npy_state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getP1_npy_state() {
        return p1_npy_state;
    }

    public void setP1_npy_state(int p1_npy_state) {
        this.p1_npy_state = p1_npy_state;
    }

    public int getP2_npy_state() {
        return p2_npy_state;
    }

    public void setP2_npy_state(int p2_npy_state) {
        this.p2_npy_state = p2_npy_state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat);
    }
}
