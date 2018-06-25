package com.krxu.dreamer.lucene.ikanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author xukuairan
 * @description 添加类描述
 * @dete ${date}
 */
public class Application {
    public static void main(String[] args) throws IOException {
        File newsFile1 = new File("D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\news.txt");
        File newsFile2 = new File("D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\news2.txt");
        File newsFile = null;
        if(new Random().nextInt(100) < 50){
            newsFile = newsFile1;
        }else{
            newsFile = newsFile2;
        }
        System.out.println("file is :" + newsFile.getName());
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(newsFile));
            String str;
            while ((str = br.readLine()) != null) {
                result.append(System.lineSeparator() + str);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Path path = Paths.get("indexdir");
        List<Map.Entry<String, Integer>> list = TextAnalyzer.getWordsRate(result.toString(), path);
        for (int i = 0; i < 10; i++) {
            if (i < list.size()) {
                System.out.println(list.get(i).getKey() + ":" + list.get(i).getValue());
            }

        }
    }

}
