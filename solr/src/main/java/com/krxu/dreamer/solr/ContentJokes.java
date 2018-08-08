package com.krxu.dreamer.solr;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/8
 * @description [添加描述]
 */
@Data
public class ContentJokes {

    @Field("id")
    private String id;

    @Field("content")
    private String content;
}
