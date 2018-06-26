package com.krxu.dreamer.ikanalyzer;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.*;

/**
 * @author xukuairan
 * @description 添加类描述
 * @dete ${date}
 */
public class Application {

    public static void main(String[] args) throws IOException {
        String contentPath = "D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\NewsContent.txt";
        String text = getContent(contentPath);

        System.out.println(segString(text));
    }

    private static List<Map.Entry<String, Integer>> segString(String content) {
        // 分词
        Reader input = new StringReader(content);
        // 智能分词关闭（对分词的精度影响很大）
        IKSegmenter iks = new IKSegmenter(input, true);
        Lexeme lexeme = null;
        Map<String, Integer> words = new HashMap<String, Integer>();
        try {
            while ((lexeme = iks.next()) != null) {
                if (words.containsKey(lexeme.getLexemeText())) {
                    words.put(lexeme.getLexemeText(), words.get(lexeme.getLexemeText()) + 1);
                } else {
                    words.put(lexeme.getLexemeText(), 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 按value排序
        List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
        Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        return sortedMap;
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
