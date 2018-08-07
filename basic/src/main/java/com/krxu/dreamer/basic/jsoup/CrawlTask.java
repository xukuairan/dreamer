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
        System.out.println(crawlContent("http://www.jokeji.cn/jokehtml/冷笑话/2018080122041383.htm"));

       //System.out.println(crawlContentUrl("http://www.jokeji.cn/list_1.htm").size());
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
            doc = Jsoup.connect(contentUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/62.0")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Encoding","gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                    .header("Cache-Control","max-age=0")
                    .header("Connection","keep-alive")
                    .header("Cookie","safedog-flow-item=; __qc_wId=2…BBQB=FNNBLJJACBFDDPMOIIGLMNKH")
                    .header("Host","www.jokeji.cn")
                    .header("If-Modified-Since","Thu, 02 Aug 2018 16:50:13 GMT")
                    .header("If-None-Match","80109ee1802ad41:0")
                    .header("Upgrade-Insecure-Requests","1")
                    .timeout(5000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(doc);
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
