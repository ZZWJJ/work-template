package com.zzw.learning.elasticsearch.service;

import com.zzw.learning.elasticsearch.entity.ArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.elasticsearch.service
 * @author:zzw
 * @createTime:2022/10/25 16:15
 * @version:1.0
 */
public interface ArticleRepository extends ElasticsearchRepository<ArticleEntity,String> {
}
