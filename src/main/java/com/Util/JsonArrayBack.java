package com.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonArrayBack {
    public static JSONObject backArray(List in) {
        String jsonString = JSON.toJSONString(in);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("back", jsonString);
        JSONObject json = new JSONObject(modelMap);
        return json;
    }
}
