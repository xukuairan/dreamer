package com.krxu.dreamer.spring.dao.query;

import lombok.Data;

import java.util.Date;

@Data
public class ContentQuery {
    private Long id;

    private Long creator;

    private Long modifier;

    private String contentName;

    private Short contentType;

    private String contentDetail;
}