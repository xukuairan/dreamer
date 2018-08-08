package com.krxu.dreamer.spring.service;

import com.krxu.dreamer.spring.dao.entity.Content;

import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/7
 * @description [添加描述]
 */
public interface ContentService {

    /**
     * 批量插入
     *
     * @param contents
     */
    int batchInsert(List<Content> contents);

    List<Content> queryAll();
}
