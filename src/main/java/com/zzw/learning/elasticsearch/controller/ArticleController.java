package com.zzw.learning.elasticsearch.controller;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.elasticsearch.controller
 * @author:zzw
 * @createTime:2022/10/25 16:15
 * @version:1.0
 */
import com.zzw.learning.elasticsearch.entity.ArticleEntity;
import com.zzw.learning.elasticsearch.service.ArticleRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description : article控制类
 * @Version : V1.0.0
 * @Date : 2021/12/22 14:11
 */
@RestController
@RequestMapping("/es")
@Api(value = "/es", tags = {"article控制类"})
public class ArticleController {
    @Resource
    private ArticleRepository articleRepository;

    /**
     * 根据文档id查询数据
     *
     * @param id 文档id
     * @return 文档详情
     */
    @GetMapping("/byId")
    @ApiOperation(value = "根据文档id查询数据", notes = "根据文档id查询数据")
    public String findById(@RequestParam String id) {
        Optional<ArticleEntity> record = articleRepository.findById(id);
        return  record.toString();
    }

    /**
     * 保存文档信息
     *
     * @param article 文档详情
     * @return 保存的文档信息
     */
    @PostMapping("/saveArticle")
    @ApiOperation(value = "保存文档信息", notes = "保存文档信息")
    public String saveArticle(@RequestBody ArticleEntity article) {
        ArticleEntity result = articleRepository.save(article);
        return result.toString();
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "删除", notes = "删除")
    public String deleteArticle(@RequestParam String id) {
        articleRepository.deleteById(id);
        return "success";
    }
}

