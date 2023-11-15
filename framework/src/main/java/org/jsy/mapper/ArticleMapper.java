package org.jsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jsy.domain.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
