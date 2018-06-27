package com.krxu.dreamer.basic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class UserProfileToProperties {
    public static void main(String[] args) {
        Map<String, String> map = xml2Map("D:\\workspace\\idea\\dreamer\\basic\\src\\main\\resources\\up2p\\pom_user_profile.xml");
        String result = getContent("D:\\workspace\\idea\\dreamer\\basic\\src\\main\\resources\\up2p\\profile.properties", map);
        System.out.println(result);

        System.out.println("###########################end");
    }

    /**
     * 文件解析成文本字符串
     *
     * @param filePath
     * @return
     */
    private static String getContent(String filePath,  Map<String, String> map) {
        File newsFile = new File(filePath);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(newsFile));
            String str;
            while ((str = br.readLine()) != null) {
                String lineResult = System.lineSeparator() + str;
                if (lineResult.trim().length() != 0 && lineResult.contains("=") && lineResult.contains("${") && lineResult.contains("}")) {
                    int start = lineResult.indexOf("{");
                    int end = lineResult.indexOf("}");
                    String key = lineResult.substring(start + 1, end);
                    String value = map.get(key);
                    if(null != value){
                        lineResult = lineResult.replace("${" + key + "}", value);
                    }
                }
                result.append(lineResult);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    public static Map<String, String> xml2Map(String xmlPath) {
        Map<String, String> map = new HashMap<>();
        try {
            DocumentBuilderFactory builderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = builderFactory.newDocumentBuilder();

            Document doc = builder.parse(new FileInputStream(xmlPath));

            //应用配置
            NodeList configNodes = doc.getElementsByTagName("properties");
            Element configElem = (Element) configNodes.item(0);
            NodeList childList = configElem.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node childNode = childList.item(i);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) childNode;
                    String tagName = element.getTagName();
                    String textContent = element.getTextContent().trim();
                    map.put(tagName, textContent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
