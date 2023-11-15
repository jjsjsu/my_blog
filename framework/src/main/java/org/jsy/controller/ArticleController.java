package org.jsy.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import org.jsy.domain.Article;
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

    @GetMapping("/list")
    private List<Article> test(){
        return service.list();
    }
}
