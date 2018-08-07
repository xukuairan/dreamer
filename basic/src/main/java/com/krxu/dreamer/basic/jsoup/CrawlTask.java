package com.krxu.dreamer.basic.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xukuairan
 * @version [版本号]
 * @date 2018/8/6
 * @description [添加描述]
 */
public class CrawlTask implements Runnable{

    private static final String TARGET_URL = "http://www.jokeji.cn";

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);


        //System.out.println(crawlContent("http://www.jokeji.cn/jokehtml/xy/2018080623100888.htm"));

        System.out.println(crawlContentUrl("http://www.jokeji.cn/list_1.htm").size());
    }

    private String crawlContentUrl;

    public CrawlTask(String crawlContentUrl) {
        this.crawlContentUrl = crawlContentUrl;
    }

    @Override
    public void run() {


    }

    /**
     * 爬内容网页列表
     *
     * @param listUrl
     * @return
     */
    public static List<String> crawlContentUrl(String listUrl){
        List<String> result = new ArrayList<>();
        Document doc ;
        try {
            doc = Jsoup.connect(listUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Elements elements = doc.body().getElementsByClass("list_title");
        Element element = elements.get(0);
        Elements liTags = element.getElementsByTag("li");

        Iterator<Element> it = liTags.iterator();
        while(it.hasNext()){
            Element li = it.next();
            result.add(TARGET_URL + li.getElementsByTag("a").attr("href"));
        }
        return result;
    }

    /**
     * 爬内容
     *
     * @param contentUrl
     * @return 需要的文本
     */
    public static List<String> crawlContent(String contentUrl){
        List<String> result = new ArrayList<>();
        Document doc ;
        try {
            doc = Jsoup.connect(contentUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Element contentsEle = doc.body().getElementById("text110");
        Elements elements = contentsEle.getElementsByTag("p");

        Iterator<Element> it = elements.iterator();
        while(it.hasNext()){
            Element element = it.next();
            result.add(element.getElementsByTag("p").text().trim().split("、")[1]);
        }
        return result;
    }
}
