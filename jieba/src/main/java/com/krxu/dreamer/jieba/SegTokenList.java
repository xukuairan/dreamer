package com.krxu.dreamer.jieba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/17
 * @description [添加描述]
 */
public class SegTokenList extends ArrayList<com.krxu.dreamer.jieba.SegToken> {

    /**
     * 过滤的词性
     */
    private String[] props = {"an", "j", "l", "n", "nr", "ns", "nt", "nz"};

    private List<String> propsList = Arrays.asList(props);

    private WordDictionary wordDictionary = WordDictionary.getInstance();

    @Override
    public boolean add(SegToken o) {
        String word = o.getWord();
        if(word.length() == 1){
            return false;
        }

        String wordProp = wordDictionary.getWordProperty(word);

        if (!propsList.contains(wordProp)) {
            return false;
        }
        if (!wordDictionary.containsWord(word)) {
            return false;
        }
        o.setProp(wordProp);
        return super.add(o);
    }
}
