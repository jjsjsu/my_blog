package org.jsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jsy.domain.Article;
import org.jsy.domain.ResponseResult;
import org.springframework.stereotype.Service;

public interface ArticleService extends IService<Article> {
    //查询热度比较高的文章
    ResponseResult hotArticleList();
    //分类查询文章列表
    ResponseResult articleList(Integer pageNUM,Integer pageSize,Long categoryId);
    //获取文章详情
    ResponseResult getArticleDetail(Long id);
}
