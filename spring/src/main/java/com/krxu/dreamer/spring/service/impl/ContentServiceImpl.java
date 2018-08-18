package com.krxu.dreamer.spring.service.impl;

import com.krxu.dreamer.spring.cache.RedisCache;
import com.krxu.dreamer.spring.dao.ContentMapper;
import com.krxu.dreamer.spring.dao.entity.Content;
import com.krxu.dreamer.spring.service.ContentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/7
 * @description [添加描述]
 */
@Service
@Log4j
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public int batchInsert(List<Content> contents) {
        return contentMapper.insertContentBatch(contents);
    }

    @Override
    public List<Content> queryAll() {
        return contentMapper.queryAll();
    }

    @Override
    @RedisCache(prefixKey = "content", expire = 1000)
    public Content getContent(Long id, Long id2) {
        Content content = contentMapper.selectByPrimaryKey(id);
        if (log.isDebugEnabled()) {
            log.debug("return content:" + content.getContentDetail());
        }
        return content;
    }


}
