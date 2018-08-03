package com.krxu.dreamer.basic.http;

import com.huawei.iread.server.domain.request.*;
import com.huawei.iread.server.domain.response.GetUserInfoResponse;
import com.huawei.iread.server.domain.response.Response;
import com.thoughtworks.xstream.XStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/2
 * @description [添加描述]
 */
public class HttpXml {


    public static void main(String[] args) {
        String url = "http://10.211.95.228:9085/csu/portalengine/miguTokenAuthenticate";


        MiguTokenAuthenticateRequest request = new MiguTokenAuthenticateRequest();
        request.setToken("STnid0000001533264473623XfiB0AoZLxTYRjL4fpJUZ8TeCkMnVbKu");
        //request.setuSessionid("9518dd9f2eca58489cb4dbeb80154217");
        GetUserInfoResponse response = (GetUserInfoResponse) sendMessageByPost(url, request);
        System.out.println(response);
    }

    public static Response sendMessageByPost(String url, Request request) {
        XStream xStream = new XStream();
        String xml = xStream.toXML(request);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        StringEntity stringEntity = new StringEntity(xml, "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        try {
            httppost.setEntity(stringEntity);
            //httppost.setHeader("x-sourceid", "223.104.247.151");
            //httppost.setHeader("x-forwarded-for", "223.104.247.151");
            httppost.setHeader("x-remoteip", "223.104.247.151");
            //httppost.setHeader("x-apptype", "1");
            //httppost.setHeader("x-userid", "cbfc6b96ed174b6398842ec5e43a9313");
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                StatusLine statusLine = response.getStatusLine();
                System.out.println("getStatusCode:" + statusLine.getStatusCode());
                HttpEntity entity = response.getEntity();

                if (entity != null && HttpStatus.SC_OK == statusLine.getStatusCode()) {
                    InputStream inputStream = entity.getContent();

                    Document doc = null;
                    try {
                        doc = new SAXReader().read(inputStream);
                    } catch (DocumentException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    System.out.println(doc);

                    return (Response) xStream.fromXML(inputStream);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
