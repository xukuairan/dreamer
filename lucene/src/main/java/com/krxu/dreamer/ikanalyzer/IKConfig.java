package com.krxu.dreamer.ikanalyzer;

import org.wltea.analyzer.cfg.Configuration;

import java.util.List;

/**
 * @author xukuairan
 * @version v5.0
 * @description ik词典配置
 * @date 2018-06-28
 */
public class IKConfig implements Configuration {

    /**
     * 分词器默认主字典路径
     */
    private static final String PATH_DIC_MAIN = "org/wltea/analyzer/dic/main2012.dic";

    /**
     * 分词器默认量词字典路径
     */
    private static final String PATH_DIC_QUANTIFIER = "org/wltea/analyzer/dic/quantifier.dic";

    /**
     * 默认使用智能分词
     *
     * @return
     */
    @Override
    public boolean useSmart() {
        return true;
    }

    @Override
    public void setUseSmart(boolean useSmart) {
    }

    @Override
    public String getMainDictionary() {
        return PATH_DIC_MAIN;
    }

    @Override
    public String getQuantifierDicionary() {
        return PATH_DIC_QUANTIFIER;
    }

    /**
     * 获取扩展词路径列表
     *
     * @return
     */
    @Override
    public List<String> getExtDictionarys() {
        return null;
    }

    /**
     * 获取扩展停用词路径列表
     *
     * @return
     */
    @Override
    public List<String> getExtStopWordDictionarys() {
        return null;
    }
}
