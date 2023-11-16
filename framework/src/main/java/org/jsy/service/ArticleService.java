package org.jsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jsy.domain.Article;
import org.jsy.domain.ResponseResult;
import org.springframework.stereotype.Service;

public interface ArticleService extends IService<Article> {
    ResponseResult<Article> hotArticleList();
}
