package com.krxu.dreamer.spring.task;

import com.krxu.dreamer.spring.dao.entity.Content;
import com.krxu.dreamer.spring.service.ContentService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/7
 * @description [添加描述]
 */
public class JokeJiCrawlTask implements Runnable {
    private static final String TARGET_URL = "http://www.jokeji.cn";

    private String listUrl;
    private ContentService contentService;

    public JokeJiCrawlTask(String listUrl, ContentService contentService) {
        this.listUrl = listUrl;
        this.contentService = contentService;
    }

    @Override
    public void run() {
        List<String> contentUrls = crawlContentUrl(listUrl);
        if(null == contentUrls){
            return;
        }
        List<Content> contents = new ArrayList<>();
        Date date = new Date();
        Long sysUser = 0L;
        String contentType = "0";
        for(String url :contentUrls){
            List<String> texts = crawlContent(url);
            if(null == texts || texts.isEmpty()){
                continue;
            }
            for(String text: texts){
                if(null == text || text.trim().length() == 0){
                    continue;
                }
                Content content = new Content();
                content.setContentName("JokeJi");
                content.setContentType(new Short(contentType));
                content.setModifier(sysUser);
                content.setCreator(sysUser);
                content.setCreateTime(date);
                content.setModifyTime(date);
                content.setContentDetail(text);
                contents.add(content);
            }
        }
        int result = 0 ;
        try{
            result = contentService.batchInsert(contents);

            System.out.println("result:" + result + ",insert:" + contents.size());
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {

        }
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
            doc = Jsoup.connect(listUrl).header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0")
                    .get();

            Elements elements = doc.body().getElementsByClass("list_title");
            Element element = elements.get(0);
            Elements liTags = element.getElementsByTag("li");

            Iterator<Element> it = liTags.iterator();
            while(it.hasNext()){
                Element li = it.next();
                result.add(TARGET_URL + li.getElementsByTag("a").attr("href"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
        Document doc = null;
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
                    .get();

            Element contentsEle = doc.body().getElementById("text110");
            Elements elements = contentsEle.getElementsByTag("p");

            Iterator<Element> it = elements.iterator();
            while(it.hasNext()){
                Element element = it.next();
                if(element.getElementsByTag("p").text().trim().contains("、")){
                    result.add(element.getElementsByTag("p").text().trim().split("、")[1]);
                }
            }
        } catch (Exception e) {
            System.out.println(contentUrl + ":" + doc);
            return null;
        }
        return result;
    }

}
