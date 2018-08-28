package com.krxu.dreamer.textanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * @author xukuairan
 * @description 添加类描述
 * @dete ${date}
 */
public class WordStatistics {

    /**
     * 词汇表
     */
    public static HashSet<String> wordLabel ;

    static{
        wordLabel = new HashSet<>();
        BufferedReader br = null;
        try {
            InputStream in =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("chinese_word.dic");
            br = new BufferedReader(new InputStreamReader(in));
            String str;
            while ((str = br.readLine()) != null) {
                wordLabel.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
        System.out.println("init lebal end ,size : " + wordLabel.size());
    }

}
