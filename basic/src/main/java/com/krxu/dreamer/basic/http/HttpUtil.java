package com.krxu.dreamer.basic.http;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/1
 * @description [添加描述]
 */
public class HttpUtil {
    private static Map<String, Object> resultMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String token = "STnid0000001533264519370vAbw8Mrbp0vHXlL4UEWDzDLjXcHrq9T8";

        Map<String, Object> header = new HashMap();
        Map<String, String> body = new HashMap();

        String TOKEN_AUTH_URL = "http://10.211.95.228:9085/csu/portalengine/miguTokenAuthenticate";

        String xml = "<Request><token>" + token + "</token></Request>";

        String respXml = null;
        OutputStreamWriter osw = null;
        OutputStream ops = null;
        InputStream is = null;
        try {
            URL url = new URL(TOKEN_AUTH_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", Integer.toString(xml.length()));
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conn.setRequestProperty("x-remoteip", "223.104.247.151");

            ops = conn.getOutputStream();
            osw = new OutputStreamWriter(ops, "UTF-8");
            osw.write(xml);
            osw.flush();

            //发送成功后，获取服务器的响应xml串：
            StringBuffer sb = new StringBuffer();
            String line;

            is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
            respXml = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ops) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (respXml == null || respXml.trim().length() == 0) {
            header.put("resultcode", 99999);
        }

        InputStream inputStream = new ByteArrayInputStream(respXml.getBytes());
        Document doc = null;
        try {
            doc = new SAXReader().read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (doc == null) {
            System.out.println("doc is null !!!");
            header.put("resultcode", 99999);
        } else {
            Element root = doc.getRootElement();
            header.put("resultcode", 200);
            body.put("loginid", root.elementText("loginId"));
            body.put("implicit", root.elementText("implicit"));
            body.put("authtype", root.elementText("authtype"));
            body.put("msisdn", root.elementText("msisdn"));
            body.put("email", root.elementText("email"));
            if (null != root.element("thirdPartyAccount")) {
                body.put("nickname", root.element("thirdPartyAccount").elementText("nickName"));
            }
        }
        resultMap.put("header", header);
        resultMap.put("body", body);
        System.out.println(resultMap);
    }
}
