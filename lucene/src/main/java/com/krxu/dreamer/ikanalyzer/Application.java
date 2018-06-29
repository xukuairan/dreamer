package com.krxu.dreamer.ikanalyzer;

import com.krxu.dreamer.ikanalyzer.doc.cfg.Configuration;
import com.krxu.dreamer.ikanalyzer.doc.cfg.DefaultConfig;
import com.krxu.dreamer.ikanalyzer.doc.core.IKSegmenter;
import com.krxu.dreamer.ikanalyzer.doc.core.Lexeme;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xukuairan
 * @description 添加类描述
 * @dete ${date}
 */
public class Application {

    private static List<String> list ;

    static Configuration cfg;

    static{
        list = new CopyOnWriteArrayList<>();
        list.add("大部分都在中国");
        list.add("光棍节阿");
        list.add("阿里巴");
        cfg = new DefaultConfig(true, list);
    }

    public static void main(String[] args) throws IOException {
        String contentPath = "NewsContent.txt";
        String text = getContent(contentPath);

        List<Map.Entry<String, Integer>> result = segString(text);

        System.out.println(result);
        System.out.println(result.size());
    }

    private static List<Map.Entry<String, Integer>> segString(String content) {
        // 分词
        Reader input = new StringReader(content);

        // 智能分词开启
        IKSegmenter iks = new IKSegmenter(input, cfg);
        Lexeme lexeme;
        Map<String, Integer> words = new HashMap<>();
        try {
            while ((lexeme = iks.next()) != null) {
                //只要中文词元
                if(Lexeme.TYPE_CNWORD != lexeme.getLexemeType()){
                    continue;
                }
                String word = lexeme.getLexemeText();

                //单个去除
                if(word.getBytes().length == 3){
                    continue;
                }

                if (words.containsKey(word)) {
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
     * @param path
     * @return
     */
    private static String getContent(String path) {
        InputStream is = Application.class.getClassLoader().getResourceAsStream(path);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
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
