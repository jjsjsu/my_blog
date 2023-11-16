package org.jsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsy.constants.SystemConstants;
import org.jsy.domain.Article;
import org.jsy.domain.Category;
import org.jsy.domain.ResponseResult;
import org.jsy.mapper.CategoryMapper;
import org.jsy.service.ArticleService;
import org.jsy.service.CategoryService;
import org.jsy.utils.BeanCopyUtils;
import org.jsy.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService  {
    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        //要求查的是getStatus字段的值为1，注意SystemConstants是我们写的一个常量类，用来解决字面值的书写问题
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //查询文章列表，条件就是上一行的articleWrapper
        List<Article> articleList=articleService.list(articleWrapper);
        //获取文章的分类id，并且去重。使用stream流来处理上一行得到的文章表集合
        Set<Long> articleIds=articleList.stream()
                //下面那行可以优化为Lambda表达式+方法引用
                .map(new Function<Article, Long>() {
                    @Override
                    public Long apply(Article article) {
                        return article.getCategoryId();
                    }
                })
                //把文章的分类id转换成Set集合
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categoryList=listByIds(articleIds);
        //注意SystemConstants是我们写的一个常量类，用来解决字面值的书写问题
        categoryList=categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成CategoryVo实体类后返回给前端，CategoryVo的作用是只返回前端需要的字段。BeanCopyUtils是我们写的工具类
        List<CategoryVO> categoryVos=BeanCopyUtils.copyBeanList(categoryList, CategoryVO.class);

        return ResponseResult.okResult(categoryVos);
    }

}
