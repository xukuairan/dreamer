package com.krxu.dreamer.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/8
 * @description [添加描述]
 */
public class HttpSolr {
    private final static String SOLR_URL = "http://localhost:8080/solr";
    private static HttpSolrServer server = new HttpSolrServer(HttpSolr.SOLR_URL);



    @Test
    public void add() throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 924);
        doc.addField("content","你猜啊啊");
        server.add(doc);
        //提交，将所有更新提交到索引中
        server.commit();
        //server.addBeans(list) 批量新增索引，list泛型实体类必须有solr field注解
    }

    @Test
    public void delete() throws IOException, SolrServerException {
        server.deleteById("924");
        server.commit();
    }

    @Test
    public void query() throws SolrServerException {
        String strQuery = "content:洗澡";
        SolrQuery query = new SolrQuery(strQuery);
        QueryResponse resp = server.query(query);
        SolrDocumentList sdList = resp.getResults();
        long totalResults = sdList.getNumFound();//命中的总记录数
        System.out.println("命中的总记录数:" + totalResults);
        List<ContentJokes>  contentJokesList = resp.getBeans(ContentJokes.class);
        System.out.println("返回集合实际大小:" + contentJokesList.size());
        System.out.println(contentJokesList);
    }
}
