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

    private static final String URL = "https://www.cnblogs.com/weknow619/p/9466198.html";

    @Test
    public void crawlMain() {
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc);
    }

}
