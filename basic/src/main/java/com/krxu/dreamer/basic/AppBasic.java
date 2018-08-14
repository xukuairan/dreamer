package com.krxu.dreamer.basic;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class AppBasic {


    public static void main(String[] args) {
        String str = "/ctr/groupId/userId";

        String[] uris = str.split("/");
        for(String uri : uris){
            System.out.println(uri);
        }
        System.out.println(str.substring(1));
    }



}

