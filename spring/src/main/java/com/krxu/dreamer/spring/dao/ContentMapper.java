package com.krxu.dreamer.spring.dao;

import com.krxu.dreamer.spring.dao.entity.Content;

import java.util.List;

public interface ContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Long id);


    int insertContentBatch(List<Content> contentList);

    List<Content> queryAll();
}