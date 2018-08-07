package com.krxu.dreamer.spring.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Content {
    private Long id;

    private Date modifyTime;

    private Date createTime;

    private Long creator;

    private Long modifier;

    private String contentName;

    private Short contentType;

    private String contentDetail;
}