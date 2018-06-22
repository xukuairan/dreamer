package com.krxu.dreamer.lucene.ikanalysis;

import com.huaban.analysis.jieba.WordStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * @author xukuairan
 * @description 添加类描述
 * @dete ${date}
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        String path = "D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\news.txt";
        List<Map.Entry<String, Integer>> results = WordStatistics.getWordFrequency(getContent(path), com.krxu.dreamer.textanalysis.WordStatistics.wordLabel);
        showTopN(results, 3);
        long a = System.currentTimeMillis();
        String path2 = "D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\news2.txt";
        List<Map.Entry<String, Integer>> results2 = WordStatistics.getWordFrequency(getContent(path2), com.krxu.dreamer.textanalysis.WordStatistics.wordLabel);
        long b = System.currentTimeMillis();
        showTopN(results2, 3);
        List<Map.Entry<String, Integer>> results3 = WordStatistics.getWordFrequency(getContent(path2), com.krxu.dreamer.textanalysis.WordStatistics.wordLabel);
        long c = System.currentTimeMillis();
        System.out.println((b - a) + " ... " + (c-b));


        while (true){
            Thread.sleep(1000);
        }
    }


    public static void showTopN(List<Map.Entry<String, Integer>> results, int N) {
        for (int i = 0; i < N && i < results.size(); i++) {
            Map.Entry<String, Integer> result = results.get(i);
            System.out.println(result.getKey() + "  " + result.getValue());
        }
    }


    public static String getContent(String path) {
        File newsFile = new File(path);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(newsFile));
            String str;
            while ((str = br.readLine()) != null) {
                result.append(System.lineSeparator() + str);
            }
            br.close();
        } catch (Exception e) {
        }
        return result.toString();
    }
}
