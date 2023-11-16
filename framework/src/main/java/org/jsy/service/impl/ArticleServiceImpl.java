package org.jsy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.jsy.constants.SystemConstants;
import org.jsy.domain.Article;
import org.jsy.domain.Category;
import org.jsy.domain.ResponseResult;
import org.jsy.mapper.ArticleMapper;
import org.jsy.service.ArticleService;

import org.jsy.service.CategoryService;
import org.jsy.utils.BeanCopyUtils;
import org.jsy.vo.ArticleListVO;
import org.jsy.vo.HotArticleVO;
import org.jsy.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult hotArticleList() {

        //查询热门文章，封装成ResponseResult返回。把所有查询条件写在queryWrapper里面
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询的不能是草稿。也就是Status字段不能是0
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序。也就是根据ViewCount字段降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只能查询出来10条消息。当前显示第一页的数据，每页显示10条数据
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT,SystemConstants.ARTICLE_STATUS_SIZE);
        page(page,queryWrapper);
        //获取最终的查询结果
        List<Article> articles = page.getRecords();
        List<HotArticleVO> articleVOS= BeanCopyUtils.copyBeanList(articles,HotArticleVO.class);

        return ResponseResult.okResult(articleVOS);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        //判空。如果前端传了categoryId这个参数，那么查询时要和传入的相同。第二个参数是数据表的文章id，第三个字段是前端传来的文章id
        articleWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //只查询状态是正式发布的文章。Article实体类的status字段跟0作比较，一样就表示是正式发布的
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop字段进行降序排序，实现置顶的文章(isTop值为1)在最前面
        articleWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page=new Page<>(pageNum,pageSize);
        page=page(page,articleWrapper);
        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理。第二种方式，用stream流的方式
         */
        //用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'
        List<Article> articles=page.getRecords();
        articles.stream()
                .map(new Function<Article, Object>() {
                    @Override
                    public Object apply(Article article) {
                        //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
                        Category category=categoryService.getById(article.getCategoryId());
                        //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                        article.setCategoryName(category.getName());
                        return article;
                    }
                }).collect(Collectors.toList());


        //把最后的查询结果封装成ArticleListVo(我们写的实体类)。BeanCopyUtils是我们写的工具类
        List<ArticleListVO> articleListVOS=BeanCopyUtils.copyBeanList(page.getRecords(),ArticleListVO.class);
        //把上面那行的查询结果和文章总数封装在PageVo(我们写的实体类)
        PageVO pageVO=new PageVO(articleListVOS,page.getTotal());
        return ResponseResult.okResult(pageVO);

    }
}
