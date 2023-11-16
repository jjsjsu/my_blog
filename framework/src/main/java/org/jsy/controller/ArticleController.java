package org.jsy.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import org.jsy.domain.Article;
import org.jsy.domain.ResponseResult;
import org.jsy.mapper.ArticleMapper;
import org.jsy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired()
    private ArticleService service;
    //测试mybatis-plus
    @GetMapping("/list")
    public List<Article> test(){
        return service.list();
    }
    //获取当前热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult<Article> hotArticleList(){
        return service.hotArticleList();
    }
    //分类获取文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId)
    {
        return service.articleList(pageNum,pageSize,categoryId);
    }
}
