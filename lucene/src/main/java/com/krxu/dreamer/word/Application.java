package com.krxu.dreamer.word;

import org.apdplat.word.WordFrequencyStatistics;
import org.apdplat.word.segmentation.SegmentationAlgorithm;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author xukuairan
 * @version xxx
 * @description 添加类描述
 * @date %date%
 */
public class Application {

    public static void main(String[] args) throws Exception {
//        //词频统计设置
//        WordFrequencyStatistics wordFrequencyStatistics = new WordFrequencyStatistics();
//        wordFrequencyStatistics.setRemoveStopWord(false);
//        wordFrequencyStatistics.setResultPath("word-frequency-statistics.txt");
//        wordFrequencyStatistics.setSegmentationAlgorithm(SegmentationAlgorithm.MaxNgramScore);
//        //开始分词
//        wordFrequencyStatistics.seg("明天下雨，结合成分子，明天有关于分子和原子的课程，下雨了也要去听课");
//        //输出词频统计结果
//        wordFrequencyStatistics.dump();
//        //准备文件
//        Files.write(Paths.get("text-to-seg.txt"), Arrays.asList("word分词是一个Java实现的分布式中文分词组件，提供了多种基于词典的分词算法，并利用ngram模型来消除歧义。"));
//        //清除之前的统计结果
//        wordFrequencyStatistics.reset();
//        //对文件进行分词
//        wordFrequencyStatistics.seg(new File("text-to-seg.txt"), new File("text-seg-result.txt"));
//        //输出词频统计结果
//        wordFrequencyStatistics.dump("file-seg-statistics-result.txt");
    }
}
