package com.zzw.learning.elasticsearch.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.elasticsearch.entity
 * @author:zzw
 * @createTime:2022/10/25 16:13
 * @version:1.0
 */
@Data
@Document(indexName = "article")
public class ArticleEntity {
    @Id
    private String id;

    private String title;

    private String content;

    private Integer userId;

    private Date createTime = DateTime.now();
}
