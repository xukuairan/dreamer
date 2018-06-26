package com.krxu.dreamer.lucene.ikanalysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xukuairan
 * @description 文本词汇分析
 * @dete ${date}
 */
public class TextAnalyzer {

    /**
     * 分析器
     */
    private static Analyzer analyzer;

    /**
     * 新建FieldType,用于指定字段索引时的信息
     */
    private  static FieldType type;

    static{
        analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String s) {
                IKTokenizer it = new IKTokenizer(false);
                return new Analyzer.TokenStreamComponents(it);
            }
        };

        type = new FieldType();
        // 索引时保存文档、词项频率、位置信息、偏移信息
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        // 原始字符串全部被保存在索引中
        type.setStored(true);
        // 存储词项量
        type.setStoreTermVectors(true);
        // 词条化
        type.setTokenized(true);
    }

    /**
     * 生成文本索引
     *
     * @param text
     * @param path
     * @throws IOException
     */
    private static void indexDocs(String text, Path path) throws IOException {
        text = delHTMLTag(text);

        // 索引的存储路径
        Directory directory = FSDirectory.open(path);

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // 索引的增删改由indexWriter创建
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        Document doc = new Document();
        Field field = new Field("content", text, type);
        doc.add(field);
        indexWriter.addDocument(doc);

        indexWriter.close();
        directory.close();
    }

    /**
     * 生成词汇频率
     *
     * @param text
     * @param path
     * @return
     * @throws IOException
     */
    public static synchronized List<Map.Entry<String, Integer>> getWordsRate(String text, Path path) throws IOException {
        indexDocs(text, path);

        Directory directory = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(directory);

        // 因为只索引了一个文档，所以DocID为0，通过getTermVector获取content字段的词项
        Terms terms = reader.getTermVector(0, "content");

        // 遍历词项
        TermsEnum termsEnum = terms.iterator();
        BytesRef thisTerm ;
        Map<String, Integer> map = new HashMap<>();
        while ((thisTerm = termsEnum.next()) != null) {
            // 词项
            String termText = thisTerm.utf8ToString();

            //单字过滤 utf-8编码格式单字占3字节,单个英文字母1字节
            if (null != termText && (termText.getBytes().length == 3
                    || termText.getBytes().length == 1)){
                continue;
            }
            // 通过totalTermFreq()方法获取词项频率
            map.put(termText, (int) termsEnum.totalTermFreq());
        }

        // 按value排序
        List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        return sortedMap;
    }

    /**
     * 去除html标签
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        String regEx_zhushi = "<!--[\\w\\W\r\\n]*?-->";
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_title = "<title[^>]*?>[\\s\\S]*?<\\/title>";
        String regEx_html = "<[^>]+>";
        Pattern p_zhushi = Pattern.compile(regEx_zhushi, 2);
        Matcher m_zhushi = p_zhushi.matcher(htmlStr);
        htmlStr = m_zhushi.replaceAll("");
        Pattern p_script = Pattern.compile(regEx_script, 2);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        Pattern p_style = Pattern.compile(regEx_style, 2);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        Pattern p_title = Pattern.compile(regEx_title, 2);
        Matcher m_title = p_title.matcher(htmlStr);
        htmlStr = m_title.replaceAll("");
        Pattern p_html = Pattern.compile(regEx_html, 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        return htmlStr.trim();
    }
}
