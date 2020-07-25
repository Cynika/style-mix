package com.sm.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

public class Photo {
    private int id;
    private String img;
    private String desc;
    private int userid;

    public Photo() {

    }

    public Photo(int id, String img, String desc, int userid) {
        this.id = id;
        this.img = img;
        this.desc = desc;
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public int getUserid() {
        return userid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat);
    }
}
