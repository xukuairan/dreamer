package com.krxu.dreamer.lucene.ikanalysis;

import java.io.*;

/**
 * @author xukuairan
 * @description 添加类描述
 * @dete ${date}
 */
public class Test {

    public static void main(String[] args) {
        File file = new File("D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\chinese_dict.txt");
        try {
            FileOutputStream fos=new FileOutputStream("D:\\workspace\\idea\\dreamer\\lucene\\src\\main\\resources\\chinese_word.dic");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            BufferedWriter bw=new BufferedWriter(outputStreamWriter);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str.split(" ")[0]);
                bw.write(str.split(" ")[0]);
                bw.newLine();
            }
            br.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println();
    }
}
