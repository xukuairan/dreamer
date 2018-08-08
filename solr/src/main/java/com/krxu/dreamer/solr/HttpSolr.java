package com.krxu.dreamer.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/8
 * @description [添加描述]
 */
public class HttpSolr {

    private final static String SOLR_URL = "http://localhost:8080/solr";

    public static void main(String[] args) throws IOException, SolrServerException {
        HttpSolrServer server = new HttpSolrServer(HttpSolr.SOLR_URL);

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 924);
        doc.addField("content","你猜啊啊");
        server.add(doc);

        server.deleteById("924");
        //提交，将所有更新提交到索引中
        server.commit();


    }
}
