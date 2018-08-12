package com.krxu.dreamer.basic.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author Administrator
 * @version [版本号]
 * @date 2018/8/10
 * @description [添加描述]
 */
public class JsoupTouTiao {

    private static final String URL = "http://news.youth.cn/sh/201808/t20180810_11693976.htm";

    @Test
    public void crawlMain(){
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.body().getElementsByClass("TRS_Editor");
        Iterator<Element> it  = elements.iterator();
        while(it.hasNext()){
            Element element = it.next();
            String re = element.getElementsByTag("p").text();
            System.out.println(re);
        }
    }

}
