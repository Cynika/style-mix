package com.Util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonBack {
    public static JSONObject back(String in) {
        JSONObject json = null;
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("back", in);
        json = new JSONObject(modelMap);
        return json;
    }
}
