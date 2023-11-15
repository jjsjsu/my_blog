package org.jsy.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsy.domain.Article;
import org.jsy.mapper.ArticleMapper;
import org.jsy.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
