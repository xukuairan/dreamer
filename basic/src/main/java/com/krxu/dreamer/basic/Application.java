package com.krxu.dreamer.basic;

import org.json.JSONObject;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class Application {


    public static void main(String[] args){

        String tokenCheckResult = "{\"body\":{},\"header\":{\"resultcode\":99999}}";

        JSONObject jsonObject = new JSONObject(tokenCheckResult);
        int resultCode = jsonObject.getJSONObject("header").getInt("resultcode");

        System.out.println(resultCode);
    }

}

