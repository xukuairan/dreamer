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
        String headerPath = "D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\NewsHeader.txt";
        String contentPath = "D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\NewsContent.txt";

        Path path = Paths.get("D:\\workspace\\idea\\dreamer\\lucene\\indexDir");

        System.out.println("###################header##############3");
        List<Map.Entry<String, Integer>> list = TextAnalyzer.getWordsRate(getContent(headerPath), path);
        for (int i = 0; i < 10; i++) {
            if (i < list.size()) {
                System.out.println(list.get(i).getKey() + ":" + list.get(i).getValue());
            }
        }
        System.out.println("###################content##############3");
        List<Map.Entry<String, Integer>> list2 = TextAnalyzer.getWordsRate(getContent(contentPath), path);
        for (int i = 0; i < 10; i++) {
            if (i < list.size()) {
                System.out.println(list2.get(i).getKey() + ":" + list2.get(i).getValue());
            }
        }

//        String content = getContent(contentPath);
//        for(int i = 0 ;i < 10000; i ++){
//            System.out.println("#####################times " + i);
//            long start = System.currentTimeMillis();
//            List<Map.Entry<String, Integer>> list3 = TextAnalyzer.getWordsRate(content, path);
//            long end = System.currentTimeMillis();
//            for (int j = 0; j < 3; j++) {
//                if (j < list.size()) {
//                    System.out.println(list3.get(j).getKey() + ":" + list3.get(j).getValue());
//                }
//            }
//            System.out.println("total:" + (end - start) + "ms");
//        }
    }




    /**
     * 文件解析成文本字符串
     *
     * @param filePath
     * @return
     */
    private static String getContent(String filePath) {
        File newsFile = new File(filePath);
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
        return result.toString();
    }

}
