package com.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sm.model.User;

import java.util.Iterator;


public class JsonInfo{
    public void getJsonObjectInfo(JSONObject jsonObject){
        Integer varcharId = (Integer)jsonObject.get("varcharId");
        JSONArray skuArray = jsonObject.getJSONArray("skuArray");
        for (Iterator iterable = skuArray.iterator();iterable.hasNext();){
            JSONObject json = (JSONObject) JSON.toJSON(iterable.next());
            User user = JSON.toJavaObject(json,User.class);
        }
    }
}
