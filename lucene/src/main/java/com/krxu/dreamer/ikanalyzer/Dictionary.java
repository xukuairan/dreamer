package com.krxu.dreamer.ikanalyzer;

import com.huaban.analysis.jieba.DictSegment;
import org.wltea.analyzer.cfg.Configuration;

/**
 * @author xukuairan
 * @version v5.0
 * @description 词典类
 * @date 2018-06-28
 */
public class Dictionary {

    /*
     * 词典单子实例
     */
    private static Dictionary singleton;

    /*
     * 主词典对象
     */
    private DictSegment _MainDict;

    /*
     * 停止词词典
     */
    private DictSegment _StopWordDict;
    /*
     * 量词词典
     */
    private DictSegment _QuantifierDict;

    /**
     * 配置对象
     */
    private Configuration cfg;
}
