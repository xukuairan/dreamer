/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 */
package com.krxu.dreamer.ikanalyzer.doc.cfg;

import java.util.List;

/**
 * 配置管理类接口
 */
public interface Configuration {

    /**
     * 返回useSmart标志位
     * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
     *
     * @return useSmart
     */
    boolean useSmart();

    /**
     * 设置useSmart标志位
     * useSmart =true ，分词器使用智能切分策略， =false则使用细粒度切分
     *
     * @param useSmart
     */
    void setUseSmart(boolean useSmart);

    /**
     * 获取主词典词汇列表
     *
     * @return
     */
    List<String> getMainDictionary();

    /**
     * 获取量词词典词汇列表
     *
     * @return
     */
    List<String> getQuantifierDicionary();

    /**
     * 获取扩展词典词汇列表
     *
     * @return
     */
    List<String> getExtDictionarys();

    /**
     * 获取扩展停止词典词汇列表
     *
     * @return
     */
    List<String> getExtStopWordDictionarys();

}