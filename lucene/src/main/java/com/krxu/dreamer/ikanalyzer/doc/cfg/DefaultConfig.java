/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package com.krxu.dreamer.ikanalyzer.doc.cfg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Configuration 默认实现
 * 2012-5-8
 *
 */
public class DefaultConfig implements Configuration {

  /*
   * 分词器默认量词字典路径
   */
  private static final String PATH_DIC_QUANTIFIER = "ikdics/quantifier.dic";

  /*
   * 是否使用smart方式分词
   */
  private boolean useSmart;

  private List<String> mainDicWords ;

  public DefaultConfig() {
  }

  public DefaultConfig(boolean useSmart, List<String> mainDicWords) {
    this.useSmart = useSmart;
    this.mainDicWords = mainDicWords;
  }

  /**
   * 返回单例
   * @return Configuration单例
   */
  public static Configuration getInstance() {
    return new DefaultConfig();
  }

  @Override
  public boolean useSmart() {
    return useSmart;
  }

  @Override
  public void setUseSmart(boolean useSmart) {
    this.useSmart = useSmart;
  }

  @Override
  public List<String> getMainDictionary() {
    return mainDicWords;
  }

  @Override
  public List<String> getQuantifierDicionary() {
    return loadDic(PATH_DIC_QUANTIFIER);
  }

  @Override
  public List<String> getExtDictionarys() {
    return new ArrayList<>();
  }

  @Override
  public List<String> getExtStopWordDictionarys() {
    return new ArrayList<>();
  }

  /**
   * 加载配置的词典
   *
   * @param path
   * @return
   */
  private List<String> loadDic(String path) {
    // 读取主词典文件
    InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
    if (is == null) {
      throw new RuntimeException("dictionary path not found,  path=" + path);
    }

    List<String> dicWords = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
      String theWord ;
      do {
        theWord = br.readLine();
        if (theWord != null && !"".equals(theWord.trim())) {
          dicWords.add(theWord.trim());
        }
      } while (theWord != null);

    } catch (IOException ioe) {
      System.err.println("dictionary path loading exception.");
      ioe.printStackTrace();
    } finally {
      try {
        if (is != null) {
          is.close();
          is = null;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return dicWords;
  }
}
